package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Batch;
import com.example.domain.Sample;
import com.example.repository.BatchRepository;

@Transactional
@Service
public class BatchService {
	@Autowired
	private BatchRepository batchRepository;
	
	public List<Sample>findAll(){
		return batchRepository.findAll();
	}
	
	public void insertCsv(Batch csvData) {
		batchRepository.insertCsv(csvData);
	}
}
