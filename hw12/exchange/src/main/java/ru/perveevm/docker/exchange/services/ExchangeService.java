package ru.perveevm.docker.exchange.services;

import org.springframework.stereotype.Service;
import ru.perveevm.docker.exchange.entities.Company;
import ru.perveevm.docker.exchange.repositories.CompanyRepository;

import java.util.List;

@Service
public class ExchangeService {
    private final CompanyRepository companyRepository;

    public ExchangeService(final CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company addCompany(final Company company) {
        if (companyRepository.findCompanyByName(company.getName()).isPresent()) {
            throw new RuntimeException("Company with such name already exists");
        }
        return companyRepository.save(company);
    }

    public Company getCompany(final String name) {
        return companyRepository.findCompanyByName(name).orElseThrow(
                () -> new RuntimeException("There is no company with such name"));
    }

    public Company addStocks(final String name, final Long stocksAmount) {
        Company company = getCompany(name);
        company.setStocks(company.getStocks() + stocksAmount);
        return companyRepository.save(company);
    }

    public Company buyStocks(final String name, final Long stocksAmount) {
        Company company = getCompany(name);
        if (company.getStocks() < stocksAmount) {
            throw new RuntimeException("This company doesn't have such amount of stocks");
        }
        company.setStocks(company.getStocks() - stocksAmount);
        return companyRepository.save(company);
    }

    public Company changeStocksPrice(final String name, final Double newPrice) {
        Company company = getCompany(name);
        company.setStockPrice(newPrice);
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
