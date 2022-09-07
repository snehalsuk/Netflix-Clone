package com.netflixclone.service;

import com.netflixclone.accessor.S3Accessor;
import com.netflixclone.accessor.VideoAccessor;
import com.netflixclone.accessor.model.VideoDTO;
import com.netflixclone.exception.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoService {

    @Autowired
    VideoAccessor videoAccessor;

    @Autowired
    S3Accessor s3Accessor;

public String getVideoUrl(final String videoId){

    VideoDTO videoDTO=videoAccessor.getVideoByVideoId(videoId);
    if(videoDTO==null){
        throw new InvalidDataException("video with videoId"+videoId+"does not exist");

    }
    String videoPath = videoDTO.getVideoPath();
    return s3Accessor.getPresignedUrl(videoPath,videoDTO.getTotalLength()*60);
}



    public String getVideoThumbnail(final String videoId){

        VideoDTO videoDTO=videoAccessor.getVideoByVideoId(videoId);
        if(videoDTO==null){
            throw new InvalidDataException("video with videoId"+videoId+"does not exist");

        }
        String thumbnailPath = videoDTO.getThumbnailPath();
        return s3Accessor.getPresignedUrl(thumbnailPath,2*60);
    }


}
