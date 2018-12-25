package com.osetrova.project.controller;

import com.osetrova.project.entity.Storage;
import com.osetrova.project.service.StorageService;
import com.osetrova.project.servicedto.GamePriceForStorageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@SessionAttributes("storage")
public class StorageController {

    private StorageService storageService;

    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/change-number-in-storage")
    public String getChangeNumberPage(@RequestParam("id") Long gamePriceId, Model model) {
        Storage currentStorage = storageService.findByGamePriceId(gamePriceId);
        model.addAttribute("storageData", GamePriceForStorageDto.builder()
                                                    .gameGamePlatformId(gamePriceId)
                                                    .build());
        model.addAttribute("storage", currentStorage);
        return "add-to-storage";
    }

    @PostMapping("/change-number-in-storage")
    public String changeNumber(GamePriceForStorageDto storageData,
                               @SessionAttribute Storage storage,
                               SessionStatus status, HttpSession session) {
        storage.setLastAdditionDate(LocalDate.now());
        storage.setNumber((short) (storage.getNumber() + storageData.getNumber()));
        storageService.addToStorage(storage);

        status.setComplete();
        session.removeAttribute("storage");
        return "redirect:/all-games";
    }
}
