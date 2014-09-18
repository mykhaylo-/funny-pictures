package com.optigra.funnypictures.service.picture;

import com.optigra.funnypictures.model.Picture;
import com.optigra.funnypictures.pagination.PagedResult;
import com.optigra.funnypictures.pagination.PagedSearch;

public interface PictureService {

	PagedResult<Picture> getPictures(PagedSearch<Picture> pagedSearch);

}