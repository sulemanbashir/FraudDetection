package com.frauddetection.enums.factory;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.frauddetection.service.entitypreprocessing.EntityPreprocessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public enum EntityPreprocessorFactory {

	AMOUNT(1) {
		@Override
		public EntityPreprocessor getEntityPreprocessor() {
			log.info("Returning EntityPreprocessor: AMOUNT");
			return entityPreprocessor;
		}
	},
	DEFAULT(0) {
		@Override
		public EntityPreprocessor getEntityPreprocessor() {
			log.info("Returning EntityPreprocessor: DEFAULT");
			return entityPreprocessor;
		}
	};

	public EntityPreprocessor entityPreprocessor;

	public void setEntityPreprocessor(EntityPreprocessor entityPreprocessor) {
		this.entityPreprocessor = entityPreprocessor;
	}

	private long entityId;

	EntityPreprocessorFactory(long i) {
		entityId = i;
	}

	public long getApiId() {
		return entityId;
	}

	public abstract EntityPreprocessor getEntityPreprocessor();

	public static EntityPreprocessorFactory getEntityPreprocessorEnum(long entityId) {
		Optional<EntityPreprocessorFactory> entity = Arrays.stream(EntityPreprocessorFactory.values()).filter(c -> c.getApiId() == entityId).findFirst();
		if (!entity.isPresent()) {
			return EntityPreprocessorFactory.DEFAULT;
		}
		return entity.get();
	}

	@Component
	public static class setEntityPreprocessorService {

		@Autowired
		private EntityPreprocessor amountPreprocessor;

		@Autowired
		private EntityPreprocessor defaultPreprocessor;

		@PostConstruct
		public void setEntityPreprocessor() {
			log.info("Initialzing EntityPreprocessor Factory Objects By Spring Autowired!");
			for (EntityPreprocessorFactory entity : EnumSet.allOf(EntityPreprocessorFactory.class)) {
				if (EntityPreprocessorFactory.AMOUNT.equals(entity)) {
					log.info("EntityPreprocessor: AMOUNT initialized successfully");
					entity.setEntityPreprocessor(amountPreprocessor);
				} else {
					log.info("EntityPreprocessor: DEFAULT initialized successfully");
					entity.setEntityPreprocessor(defaultPreprocessor);
				}

			}
		}
	}

}
