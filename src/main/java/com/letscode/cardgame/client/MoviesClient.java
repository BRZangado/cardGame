package com.letscode.cardgame.client;

import com.letscode.cardgame.dto.MovieDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "MoviesClient", url = "http://www.omdbapi.com/")
public interface MoviesClient {

    @RequestMapping(method = RequestMethod.GET)
    MovieDTO getMovie(@RequestParam String i, @RequestParam String apikey);
}
