package com.proyecto.user.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.proyecto.rest.resource.user.dto.TheoreticalPortfolioDTO;

public class InvestorProfile {
	private static String investorProfileFile = "investor-profiles-and-theoretical-portfolios.xml";
	private static List<TheoreticalPortfolioDTO> agressiveInvestor;
	private static List<TheoreticalPortfolioDTO> moderateInvestor;
	private static List<TheoreticalPortfolioDTO> conservativeInvestor;
	
	private String name;
	private String description;
	private Integer amount;
	private Integer period;
	private Double yield;
	private Integer score;
	private InvestorProfileEnum profile;
	private String profileString;
	
	
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
	
	public static void loadXMLFile(String filename){
		investorProfileFile = filename;
		loadXmlFile();
	}
	
	private static List<TheoreticalPortfolioDTO> loadInvestorProfilePortfolio(
			XMLConfiguration config, String profile){
		
		List<TheoreticalPortfolioDTO> portfolios = new ArrayList<TheoreticalPortfolioDTO>();
		
		config.configurationAt("agressive");
		HierarchicalConfiguration baseNode = config.configurationAt(profile);
		
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

	public static List<TheoreticalPortfolioDTO> getAgressiveInvestor() {
		return agressiveInvestor;
	}

	public static List<TheoreticalPortfolioDTO> getModerateInvestor() {
		return moderateInvestor;
	}

	public static List<TheoreticalPortfolioDTO> getConservativeInvestor() {
		return conservativeInvestor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Double getYield() {
		return yield;
	}

	public void setYield(Double yield) {
		this.yield = yield;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public InvestorProfileEnum getProfile() {
		return profile;
	}

	public void setProfile(InvestorProfileEnum profile) {
		this.profile = profile;
	}

	public String getProfileString() {
		return profileString;
	}

	public void setProfileString(String profileString) {
		this.profileString = profileString;
	}
	
	public void resolveInvestorProfile(Integer score) {
		this.score = score;
		if (score <= 4) {
			this.profile = InvestorProfileEnum.CONSERVATIVE;
			this.profileString = "Conservative";
		} else if (score <= 8) {
			this.profile = InvestorProfileEnum.MODERATE;
			this.profileString = "Moderate";
		} else {
			this.profile = InvestorProfileEnum.AGRESSIVE;
			this.profileString = "Agressive";
		}
	}
}
