CREATE TABLE `image` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `parent` BIGINT NULL,
  `userId` BIGINT NOT NULL,
  `albumId` BIGINT NOT NULL,
  `extension` CHAR(5) NOT NULL,
  `initialName` CHAR(40) NULL,
  `imageName` CHAR(2) NULL,
  `path` CHAR(100) NULL,
  `height` INT NOT NULL,
  `width` INT NOT NULL,
  `imageType` TINYINT(1) NOT NULL,
  `dateAdd` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));