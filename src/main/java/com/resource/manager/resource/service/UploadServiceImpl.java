package com.resource.manager.resource.service;

import com.resource.manager.resource.entity.Project;
import com.resource.manager.resource.entity.Record;
import com.resource.manager.resource.repository.ProjectRepository;
import com.resource.manager.resource.repository.RecordRepository;
import com.resource.manager.resource.exception.FileStorageException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public UploadServiceImpl(RecordRepository recordRepository, ProjectRepository projectRepository) {
		this.recordRepository = recordRepository;
		this.projectRepository = projectRepository;
	}

	public static boolean isInteger(String index) {
		try {

			Integer.parseInt(index);
			return true;

		} catch (Exception ex) {

			return false;

		}
	}

	@Override
	public List<Record> saveFileRecords(MultipartFile file) {
		List<Record> myList = new ArrayList<Record>();

		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		InputStream inStream = null;
		BufferedReader myReader = null;
		String colKeys = "";
		String fileLine = "";

		/*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String encryptedFilename = filename + "#" + timeStamp;*/

		try {

			if (filename.contains("..")) {
				String message = String.format("File: %s contains invalid characters in its filename!", filename);
				throw new FileStorageException(message);
			}

			if (!(filename.contains(".csv"))) {
				String message = String.format("File: %s is not a valid csv file!", filename);
				throw new FileStorageException(message);
			}

			inStream = file.getInputStream();
			myReader = new BufferedReader(new InputStreamReader(inStream));

			fileLine = myReader.readLine();
			colKeys = fileLine;

			while ((fileLine = myReader.readLine()) != null) {
				Project newCsvProject = projectRepository.save(new Project());
				newCsvProject.setFilename(filename);

				Record newCsvProjectRecord = new Record();
				newCsvProjectRecord.setType("project");
				newCsvProjectRecord.setTypeId(newCsvProject.getId());
				newCsvProjectRecord.setKeys(colKeys);
				newCsvProjectRecord.setKeyValues(fileLine);

				List<String> keyValuesList = new ArrayList<String>(
						Arrays.asList(newCsvProjectRecord.getKeyValues().split(",")));
				List<String> dataTypesList = new ArrayList<String>();

				for (int i = 0; i < keyValuesList.size(); i++) {
					if (isInteger(keyValuesList.get(i))) {
						dataTypesList.add("number");
					} else {
						dataTypesList.add("text");
					}
				}

				newCsvProjectRecord.setDataTypes(String.join(",", dataTypesList));
				recordRepository.save(newCsvProjectRecord);

				myList.add(newCsvProjectRecord);
			}

		} catch (IOException ex) {

			ex.printStackTrace();

		}

		return myList;
	}
}