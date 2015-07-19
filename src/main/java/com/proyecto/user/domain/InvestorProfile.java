package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationBuilder;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.HierarchicalConfigurationXMLReader;
import org.apache.commons.configuration.XMLConfiguration;

import com.proyecto.rest.resource.user.dto.TheoreticalPortfolioDTO;

public class InvestorProfile {
	private static String investorProfileFile = "investor-profiles-and-theoretical-portfolios.xml";
	private static List<TheoreticalPortfolioDTO> agressiveInvestor;
	private static List<TheoreticalPortfolioDTO> moderateInvestor;
	private static List<TheoreticalPortfolioDTO> conservativeInvestor;
	
	
	public static void loadXmlFile(){
		
		
		XMLConfiguration config;
		try {
			config = new XMLConfiguration(investorProfileFile);
		} catch (ConfigurationException e) {
			e.printStackTrace();
			return;
		}
		
		agressiveInvestor = loadInvestorProfilePortfolio(config, "agressive");
		moderateInvestor = loadInvestorProfilePortfolio(config, "moderate");
		conservativeInvestor = loadInvestorProfilePortfolio(config, "conservative");
		
	}
	
	private static List<TheoreticalPortfolioDTO> loadInvestorProfilePortfolio(
			XMLConfiguration config, String profile){
		
		List<TheoreticalPortfolioDTO> portfolios = new ArrayList<TheoreticalPortfolioDTO>();
		

		HierarchicalConfiguration baseNode = config.configurationAt("investorProfile." + profile);
		
		//iterar sobre cada portfolio(no asumimos una cantidad fija)
		for(HierarchicalConfiguration portfolioNode : baseNode.configurationsAt("portfolio")){
			TheoreticalPortfolioDTO portfolioDTO = new TheoreticalPortfolioDTO();
			//y por cada asset dentro del portfolio
			for(HierarchicalConfiguration assetNode : portfolioNode.configurationsAt("asset")){
				portfolioDTO.getAssetTypeAndPercentage().put(
						assetNode.getString("subtype"), 
						assetNode.getInteger("percentage", 0));
			}
			
			portfolios.add(portfolioDTO);
		}
		
		return portfolios;
	}
}
