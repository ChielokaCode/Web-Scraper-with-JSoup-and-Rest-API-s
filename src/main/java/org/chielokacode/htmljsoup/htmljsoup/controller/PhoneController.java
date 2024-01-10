package org.chielokacode.htmljsoup.htmljsoup.controller;

import org.chielokacode.htmljsoup.htmljsoup.model.Phone;
import org.chielokacode.htmljsoup.htmljsoup.service.PhoneServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/phones")
public class PhoneController {

    private final PhoneServiceImpl phoneService;

    @Autowired
    public PhoneController(PhoneServiceImpl phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/baseUrl")
    public ResponseEntity<String> getAbsoluteUrl() throws IOException {
        String response = phoneService.getAbsoluteUrl();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/sub/{subId}")
    public ResponseEntity<List<Phone>> getPhoneDetails(@PathVariable String categoryId,
                                                        @PathVariable String subId) throws IOException {
        List<Phone> response= phoneService.getPhoneLists(categoryId, subId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
