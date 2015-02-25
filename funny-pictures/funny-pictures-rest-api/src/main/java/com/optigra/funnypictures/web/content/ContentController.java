package com.optigra.funnypictures.web.content;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.optigra.funnypictures.facade.facade.content.ContentFacade;
import com.optigra.funnypictures.facade.resources.content.ContentResource;
import com.optigra.funnypictures.model.content.MimeType;
import com.optigra.funnypictures.web.BaseController;

/**
 * Controller, that handles all content operation 
 * like read(get content file) or write(store content).
 * @author ivanursul
 *
 */
@RestController
@RequestMapping(value = "/content")
public class ContentController extends BaseController {

	@Resource(name = "contentFacade")
	private ContentFacade contentFacade;
	

	/**
	 * Method for getting file by its contentPath.
	 * @param response HttpServletResponse, that is needed because of direct write 
	 * to response. 
	 * @param contentPath path of that content.
	 * @throws Exception any exception. All exceptions are handled by BaseController.
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET)
	public void getContentByPath(final HttpServletResponse response, @RequestParam("contentPath") final String contentPath) throws Exception {
		ContentResource contentResource = contentFacade.getContent(contentPath);

		InputStream in = contentResource.getContentStream();
		OutputStream out = response.getOutputStream();

		response.setHeader(HttpHeaders.CONTENT_TYPE, contentResource.getMimeType().getType());

		IOUtils.copy(in, out);
		IOUtils.closeQuietly(in);
	}

	/**
	 * Method for storing file content.
	 * @param file appropriate file
	 * @return ContentResource(all information about stored content)
	 * @throws Exception any exception. All exceptions are handled by BaseController.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, headers = { "content=file" })
	public ContentResource postContent(@RequestParam("content") final MultipartFile file) throws Exception {

		InputStream istream = file.getInputStream();

		MimeType mimeType = MimeType.fromType(file.getContentType());

		ContentResource contentResource = new ContentResource();
		contentResource.setContentStream(istream);
		contentResource.setMimeType(mimeType);

		return contentFacade.storeContent(contentResource);
	}

	/**
	 * Method for storing file content from given URL.
	 * @param url appropriate file url
	 * @return ContentResource(all information about stored content)
	 * @throws Exception any exception. All exceptions are handled by BaseController.
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST, headers = { "content=url" })
	public ContentResource createContentFromUrl(@RequestParam("url") final String url) throws Exception {
		URL urlToUrl = new URL(url);
		urlToUrl.openConnection();
	    HttpURLConnection connection = (HttpURLConnection) urlToUrl.openConnection();
	    connection.setRequestMethod("HEAD");
	    connection.connect();
	    String contentType = connection.getContentType();
	    if (!contentType.startsWith("image")) {
	        throw new Exception("Requested url is not an image!");
	    }
		
		InputStream istream = urlToUrl.openStream();

		MimeType mimeType = MimeType.fromType(contentType);

		ContentResource contentResource = new ContentResource();
		contentResource.setContentStream(istream);
		contentResource.setMimeType(mimeType);

		return contentFacade.storeContent(contentResource);
	}
}
