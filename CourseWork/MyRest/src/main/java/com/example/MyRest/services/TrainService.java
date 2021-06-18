package com.example.MyRest.services;

import com.example.MyRest.entities.Train;
import com.example.MyRest.repositories.TrainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<Train> readAll(){
        return trainRepository.findAll();
    }

    public void create(Train train){
        trainRepository.save(train);
    }

    public Train read(long trainId) {
        return trainRepository.getOne(trainId);
    }

    public boolean update(Train train, long trainId) {
        if (trainRepository.existsById(trainId)) {
            train.setTrainId(trainId);
            trainRepository.save(train);
            return true;
        }

        return false;
    }

    public boolean delete(long trainId) {
        if (trainRepository.existsById(trainId)) {
            trainRepository.deleteById(trainId);
            return true;
        }
        return false;
    }
}
