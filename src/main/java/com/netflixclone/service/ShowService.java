package com.netflixclone.service;

import com.netflixclone.accessor.ShowAccessor;
import com.netflixclone.accessor.model.ShowDTO;
import com.netflixclone.controller.model.ShowOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShowService {

    @Autowired
    private ShowAccessor showAccessor;

    @Autowired
    private ShowMapper showMapper;

    public List<ShowOutput> getShowByName(final String showName)
    {
        List<ShowDTO> showDTOList = showAccessor.getShowByName(showName);
        List<ShowOutput> showOutput = new ArrayList<>();
        for (ShowDTO showDTO : showDTOList) {
            showOutput.add(showMapper.mapShowDtoToOutput(showDTO));
        }
        return showOutput;

    }
}