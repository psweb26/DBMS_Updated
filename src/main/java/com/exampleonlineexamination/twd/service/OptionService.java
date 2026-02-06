package com.exampleonlineexamination.twd.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.exampleonlineexamination.twd.model.Option;
import com.exampleonlineexamination.twd.repository.OptionRepository;

@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public List<Option> getAllOptions() {
        return optionRepository.findAll();
    }

    public Option getOptionById(Integer id) {
        return optionRepository.findById(id).orElse(null);
    }

    public List<Option> getOptionsByQuestionId(Integer questionId) {
        return optionRepository.findByQuestionId(questionId);
    }

    public List<Option> getCorrectAnswers(Integer questionId) {
        return optionRepository.findByQuestionIdAndIsCorrect(questionId, true);
    }

    public Option addOption(Option option) {
        return optionRepository.save(option);
    }

    public Option updateOption(Integer id, Option optionDetails) {
        Option option = optionRepository.findById(id).orElse(null);
        if (option != null) {
            option.setQuestionId(optionDetails.getQuestionId());
            option.setOptionText(optionDetails.getOptionText());
            option.setIsCorrect(optionDetails.getIsCorrect());
            return optionRepository.save(option);
        }
        return null;
    }

    public void deleteOption(Integer id) {
        optionRepository.deleteById(id);
    }

    public void deleteOptionsByQuestionId(Integer questionId) {
        List<Option> options = optionRepository.findByQuestionId(questionId);
        optionRepository.deleteAll(options);
    }
}
