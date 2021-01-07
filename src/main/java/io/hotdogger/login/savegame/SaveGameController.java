package io.hotdogger.login.savegame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saves")
public class SaveGameController {

    @Autowired
    private SaveGameService saveGameService;

}
