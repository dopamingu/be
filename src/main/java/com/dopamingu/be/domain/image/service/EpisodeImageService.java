package com.dopamingu.be.domain.image.service;

import com.dopamingu.be.domain.episode.domain.Episode;
import com.dopamingu.be.domain.image.dao.EpisodeImageRepository;
import com.dopamingu.be.domain.image.domain.EpisodeImage;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class EpisodeImageService {

    private final EpisodeImageRepository episodeImageRepository;

    public EpisodeImageService(EpisodeImageRepository episodeImageRepository) {
        this.episodeImageRepository = episodeImageRepository;
    }

    public void deleteRelatedEpisodeImages(Episode episode) {
        // episode 기준으로 찾기
        List<EpisodeImage> episodeImageList =
            episodeImageRepository.findEpisodeImagesByEpisode(episode);
        // 관련 EpisodeImage 가 있으면 삭제하기
        episodeImageRepository.deleteAll(episodeImageList);
    }

    public Set<EpisodeImage> createMultipleEpisodeImage(
        Episode episode, List<String> imageUrlList) {

        Set<EpisodeImage> episodeImageSet =
            imageUrlList.stream()
                .map(
                    imageUrl ->
                        EpisodeImage.builder()
                            .imageUrl(imageUrl)
                            .episode(episode)
                            .build())
                .collect(Collectors.toSet());

        episodeImageRepository.saveAll(episodeImageSet);
        return episodeImageSet;
    }
}
