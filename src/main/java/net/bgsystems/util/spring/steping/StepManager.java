package net.bgsystems.util.spring.steping;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import net.bgsystems.util.ExceptionUtils;
import net.bgsystems.util.ReRunException;
import net.bgsystems.util.SystemUtils.SystemEndCode;

/**
 * Clase abstracta maneja los steps definidos (como bean "stepInfoCollection" de tipo
 * Set&lt;StepInformation&gt;) en el contexto Spring y los ejecuta de manera
 * ordenada.
 * 
 * Para usarse se requiere implementar y anotar con {@link org.springframework.stereotype.Component @Component} o algun derivado.
 * 
 * 
 * @author Brian Gabriel Silva
 *
 */
public abstract class StepManager {
	private static final Logger LOGGER = LogManager.getLogManager().getLogger(StepManager.class.getName());

	/**
	 * Lista inmutable y ordenada de steps
	 */
	@Resource(name = "stepInfoCollection")
	Set<StepInformation> stepInfoCollection;

	@Autowired
	ApplicationContext appContext;

	@PostConstruct
	private void configure() throws ExecutionException {
		if (this.stepInfoCollection == null || this.stepInfoCollection.isEmpty())
			throw new ExecutionException("No hay steps para ejecutar", null);

		GenericApplicationContext gac = (GenericApplicationContext) appContext;

		for (StepInformation stepInfo : stepInfoCollection) {
			gac.registerBean(stepInfo.getClazz(), (beanDefinition) -> {
				beanDefinition.setBeanClassName(stepInfo.getClazz().getName());
				beanDefinition.setLazyInit(true);
			});
		}
	}

	public SystemEndCode ejecutarSteps() {
		SystemEndCode endCode = SystemEndCode.OK;
		try {
			LOGGER.info("Se van a ejecutar los steps en el siguiente orden: " + stepInfoCollection);
			for (StepInformation stepInfo : stepInfoCollection) {
				Step step = (Step) appContext.getBean(stepInfo.getClazz());
				LOGGER.info("Inicia step " + stepInfo.getName());
				step.ejecutar();
				LOGGER.info("Step " + stepInfo.getName() + " termino!");
			}
		} catch (StepException e) {
			LOGGER.log(Level.FINE, ExceptionUtils.getMessage(e), e);
			if (e.getCause() instanceof ReRunException) {
				LOGGER.log(Level.WARNING, "Se volvera a ejecutar el proceso");
				endCode = SystemEndCode.RE_RUN;
			} else {
				LOGGER.log(Level.SEVERE, "Ocurrio un error en el step " + e.getStepName() + " con el siguiente mensaje \"" + e.getMessage() + "\"", e);
				endCode = SystemEndCode.ERROR;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, ExceptionUtils.getMessage(e), e);
			endCode = SystemEndCode.ERROR;
		} finally {
			LOGGER.info("Se termino de ejecutar todos los steps");
		}
		return endCode;
	}
}
