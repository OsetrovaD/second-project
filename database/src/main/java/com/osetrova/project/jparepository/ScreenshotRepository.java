package com.osetrova.project.jparepository;

import com.osetrova.project.entity.Screenshot;
import com.osetrova.project.entity.embeddable.GameScreenshot;
import org.springframework.data.repository.CrudRepository;

public interface ScreenshotRepository extends CrudRepository<Screenshot, GameScreenshot> {
}
