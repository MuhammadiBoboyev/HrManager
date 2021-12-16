package uz.pdp.lesson_5.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.lesson_5.entity.InputOutputTourniquetScanner;
import uz.pdp.lesson_5.entity.Task;
import uz.pdp.lesson_5.entity.User;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiEmployeeDataResponse {
    User employee;
    private List<Task> taskList;
    List<InputOutputTourniquetScanner> inputOutputTourniquetScanners;
}
