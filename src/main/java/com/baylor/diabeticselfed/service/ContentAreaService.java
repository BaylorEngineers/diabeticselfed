package com.baylor.diabeticselfed.service;

import com.baylor.diabeticselfed.dto.ContentAreaDTO;
import com.baylor.diabeticselfed.entities.ContentArea;
import com.baylor.diabeticselfed.repository.ContentAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentAreaService {

    @Autowired
    private ContentAreaRepository contentAreaRepository;

    public ContentArea createContentArea(String name) {
        ContentArea contentArea = new ContentArea();
        contentArea.setName(name);
        return contentAreaRepository.save(contentArea);
    }

    public List<ContentArea> getAllContentAreas() {
        return contentAreaRepository.findAll();
    }

    public List<ContentAreaDTO> getAllContentAreasDTO() {
        return contentAreaRepository.findAll().stream()
                .map(area -> new ContentAreaDTO(area.getId(), area.getName()))
                .collect(Collectors.toList());
    }
}
