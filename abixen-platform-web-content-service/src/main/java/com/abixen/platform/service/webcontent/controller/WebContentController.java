/**
 * Copyright (c) 2010-present Abixen Systems. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.abixen.platform.service.webcontent.controller;

import com.abixen.platform.service.webcontent.converter.WebContentToWebContentDtoConverter;
import com.abixen.platform.service.webcontent.dto.WebContentDto;
import com.abixen.platform.service.webcontent.form.WebContentForm;
import com.abixen.platform.service.webcontent.model.impl.WebContent;
import com.abixen.platform.service.webcontent.service.WebContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/service/abixen/web-content/control-panel/web-contents")
public class WebContentController {

    private final WebContentService webContentService;
    private final WebContentToWebContentDtoConverter webContentToWebContentDtoConverter;

    @Autowired
    public WebContentController(WebContentService webContentService,
                                WebContentToWebContentDtoConverter webContentToWebContentDtoConverter) {
        this.webContentService = webContentService;
        this.webContentToWebContentDtoConverter = webContentToWebContentDtoConverter;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<WebContentDto> getWebContents(@PageableDefault(size = 1) Pageable pageable) {
        log.debug("getWebContents()- pageable: {}", pageable);

        Page<WebContent> webContents = webContentService.getWebContents(pageable);
        Page<WebContentDto> webContentsDtos = webContentToWebContentDtoConverter.convertToPage(webContents);

        return webContentsDtos;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WebContentForm getWebContent(@PathVariable("id") Long id) {
        return webContentService.getWebContent(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteWebContent(@PathVariable("id") Long id) {
        webContentService.deleteWebContent(id);
    }
}