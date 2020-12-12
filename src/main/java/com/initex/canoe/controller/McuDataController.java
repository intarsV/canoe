package com.initex.canoe.controller;

import com.initex.canoe.dto.McuDataDTO;
import com.initex.canoe.services.McuDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping(value = "/api/v1/mcuData")
@CrossOrigin(allowedHeaders = "*")
public class McuDataController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    private McuDataService service;

    @Autowired
    public McuDataController(McuDataService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<McuDataDTO> getMcuData(@RequestParam final Boolean isDone) {
        return service.getMcuData(isDone);
    }

    @GetMapping(value = "/emitter")
    public SseEmitter handle(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        SseEmitter emitter = new SseEmitter(54000000L);
        this.emitters.add(emitter);
        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));
        return emitter;
    }

    @PostMapping
    @ResponseBody
    public McuDataDTO addMcuData(@RequestBody final McuDataDTO mcuDataDTO) {
        return service.addMcuData(mcuDataDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public McuDataDTO updateMcuData(@PathVariable String id, @RequestBody final McuDataDTO mcuDataDTO) {
        return service.updateMcuData(id, mcuDataDTO);
    }

    @EventListener
    public void onMemoryInfo(McuDataDTO mcuDataDTO) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        this.emitters.forEach(emitter -> {
            try {
                SseEmitter.SseEventBuilder builder = SseEmitter.event()
                        .data(mcuDataDTO)
                        .name("mcuData")
                        .reconnectTime(10_000L);
                emitter.send(builder);
            }
            catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });
        this.emitters.removeAll(deadEmitters);
    }
}
