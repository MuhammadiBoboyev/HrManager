package uz.pdp.lesson_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.lesson_5.entity.*;
import uz.pdp.lesson_5.payload.ApiResponse;
import uz.pdp.lesson_5.payload.TourniquetScannerDto;
import uz.pdp.lesson_5.repository.*;

import java.util.Optional;

@Service
public class TourniquetScannerService {
    @Autowired
    TourniquetScannerRepository tourniquetScannerRepository;

    @Autowired
    TourniquetRepository tourniquetRepository;

    public ApiResponse tourniquetScanner(TourniquetScannerDto tourniquetScannerDto) {
        Optional<Tourniquet> optionalTourniquet = tourniquetRepository.findById(tourniquetScannerDto.getTourniquetId());
        if (!optionalTourniquet.isPresent())
            return new ApiResponse("tourniquet is fake", false);
        InputOutputTourniquetScanner inputOutputTourniquetScanner = new InputOutputTourniquetScanner();
        inputOutputTourniquetScanner.setTourniquet(optionalTourniquet.get());
        inputOutputTourniquetScanner.setInOut(tourniquetScannerDto.isInOut());
        inputOutputTourniquetScanner.setTime(tourniquetScannerDto.getTime());
        tourniquetScannerRepository.save(inputOutputTourniquetScanner);
        return new ApiResponse("success", false);
    }
}
