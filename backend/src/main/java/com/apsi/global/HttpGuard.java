package com.apsi.global;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.concurrent.Callable;

public class HttpGuard {
	private static Logger logger = LogManager.getLogger(HttpGuard.class);

	/**
	 * tries to run given runnable and if it returns OK if no exceptions were thrown
	 * or BAD_REQUEST otherwise
	 */
	public static ResponseEntity<?> getResponse(Runnable function) {
		try {
			function.run();
			return ResponseEntity.ok(new OkResponse());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ErrorResponse.getEntity(HttpStatus.BAD_REQUEST, "", e.getMessage());
		}
	}

	/**
	 * tries to run given callable and if it returns OK response and price of callable in response
	 * if no exceptions were thrown or BAD_REQUEST otherwise
	 */
	public static ResponseEntity<?> getResponse(Callable function) {
		try {
			var result = function.call();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			logger.error(e.getClass().getName(), e);
			return ErrorResponse.getEntity(HttpStatus.BAD_REQUEST, "", e.getMessage());
		}
	}

//	/**
//	 * Tries to run given callable and return a file for download.
//	 *
//	 * @return OK response with file download, BAD_REQUEST if exceptions occured
//	 */
//	public static ResponseEntity<?> getFile(Callable<DatabaseFile> function) {
//		try {
//			DatabaseFile databaseFile = function.call();
//			var resource = new ByteArrayResource(databaseFile.getFileAsByteArray());
//			return ResponseEntity.ok()
//					.contentLength(databaseFile.getLength())
//					.contentType(MediaType.parseMediaType(databaseFile.getFileFormat()))
//					.header(HttpHeaders.CONTENT_TYPE, databaseFile.getFileFormat())
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + databaseFile.getFileName())
//					.body(resource);
//		} catch (Exception e) {
//			logger.error(e.getClass().getName(), e);
//			return ErrorResponse.getEntity(HttpStatus.BAD_REQUEST, "", e.getMessage());
//		}
//	}
}

