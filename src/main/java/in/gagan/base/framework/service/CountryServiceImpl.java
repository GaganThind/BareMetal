package in.gagan.base.framework.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import in.gagan.base.framework.dao.CountryDAO;
import in.gagan.base.framework.entity.Country;

@Transactional
@Service
public class CountryServiceImpl implements CountryService {
	
	private final CountryDAO countryDAO;
	
	@Autowired
	public CountryServiceImpl(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}
	
	@Value("${application.countries.file.location}")
	private String fileLocation;

	/**
	 * Method used to load country data from csv file.
	 */
	@Override
	public void loadCountriesFromCSV() {
		File initialFile = new File(fileLocation);
		List<Country> countries = null;
	    
		try {
			InputStream targetStream = new FileInputStream(initialFile);
			countries = CsvUtils.read(Country.class, targetStream);
		} catch(IOException e) {
			throw new IllegalArgumentException("Data csv containing Country data is not found or is not able to read");
		}
		
		if (null == countries) {
			throw new IllegalArgumentException("Country list is empty, Kindly check the data csv file");
		}
		
		this.countryDAO.saveAll(countries);
	}

}

class CsvUtils {
	
    private static final CsvMapper mapper = new CsvMapper();
    
    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }
}
