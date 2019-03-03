package com.memory.platform.module.trace.dto;

import java.util.ArrayList;
import java.util.List;

public class ReGeoResponseDTO extends ResponseBaseDTO {
	

	//返回格式
	/*{
	    "status": "1",
	    "info": "OK",
	    "infocode": "10000",
	    "regeocodes": [
	        {
	            "formatted_address": "重庆市渝北区龙塔街道兴盛大道33附53-54号上品.拾陆",
	            "addressComponent": {
	                "country": "中国",
	                "province": "重庆市",
	                "city": [
	                    
	                ],
	                "citycode": "023",
	                "district": "渝北区",
	                "adcode": "500112",
	                "township": "龙塔街道",
	                "towncode": "500112013000",
	                "neighborhood": {
	                    "name": [
	                        
	                    ],
	                    "type": [
	                        
	                    ]
	                },
	                "building": {
	                    "name": [
	                        
	                    ],
	                    "type": [
	                        
	                    ]
	                },
	                "streetNumber": {
	                    "street": "兴盛大道",
	                    "number": "33附53-54号",
	                    "location": "106.546421,29.5892569",
	                    "direction": "南",
	                    "distance": "21.4222"
	                },
	                "businessAreas": [
	                    {
	                        "location": "106.53832759259258,29.590372836419753",
	                        "name": "黄泥塝",
	                        "id": "500112"
	                    },
	                    {
	                        "location": "106.55894909259257,29.580763614814824",
	                        "name": "五里店",
	                        "id": "500105"
	                    },
	                    {
	                        "location": "106.53611417427386,29.581412792531104",
	                        "name": "兴隆",
	                        "id": "500105"
	                    }
	                ]
	            }
	        }]*/
	
	public static class AddressComponent{
		//"country": "中国",
		String country;
		//"province": "重庆市",
		String province;
		
		String  citycode;
		//"district": "渝北区",
		String  district;
		//"adcode": "500112",
		String  adcode;
		//"township": "龙塔街道",
		String  township;
		//"towncode": "500112013000",
		String  towncode;
		StreetNumber streetNumber;
		public String getAdcode() {
			return adcode;
		}
		public String getCitycode() {
			return citycode;
		}
		public String getCountry() {
			return country;
		}
		public String getDistrict() {
			return district;
		}
		public String getProvince() {
			return province;
		}
		public StreetNumber getStreetNumber() {
			return streetNumber;
		}
		public String getTowncode() {
			return towncode;
		}
		public String getTownship() {
			return township;
		}
		public void setAdcode(String adcode) {
			this.adcode = adcode;
		}
		public void setCitycode(String citycode) {
			this.citycode = citycode;
		}
		public void setCountry(String country) {
			this.country = country;
		} 
		public void setDistrict(String district) {
			this.district = district;
		}
        public void setProvince(String province) {
			this.province = province;
		}
        public void setStreetNumber(StreetNumber streetNumber) {
			this.streetNumber = streetNumber;
		}
        public void setTowncode(String towncode) {
			this.towncode = towncode;
		}
		public void setTownship(String township) {
			this.township = township;
		}
	}
	public static class BusinessAreas{
//		"location": "106.53611417427386,29.581412792531104",
//        "name": "兴隆",
//        "id": "500105"
		String location;
		String name;
		String id;
		public String getId() {
			return id;
		}
		public String getLocation() {
			return location;
		}
		public String getName() {
			return name;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	public static class Regeocode{
		String formatted_address;
		AddressComponent addressComponent;
		public AddressComponent getAddressComponent() {
			return addressComponent;
		}
		public void setAddressComponent(AddressComponent addressComponent) {
			this.addressComponent = addressComponent;
		}
		public String getFormatted_address() {
			return formatted_address;
		}
		public void setFormatted_address(String formatted_address) {
			this.formatted_address = formatted_address;
		}
	}
	
	public static class StreetNumber{
		 //"street": "兴盛大道",
		String street;
//         "number": "33附53-54号",
		String number;
//         "location": "106.546421,29.5892569",
		String location;
//         "direction": "南",
		String direction;
		//         "distance": "21.4222"
		String distance;
		List<BusinessAreas> businessAreas=new ArrayList<>();
		public List<BusinessAreas> getBusinessAreas() {
			return businessAreas;
		}
		public String getDirection() {
			return direction;
		}
		public String getDistance() {
			return distance;
		}
		public String getLocation() {
			return location;
		}
		public String getNumber() {
			return number;
		}
		public String getStreet() {
					return street;
				}
		public void setBusinessAreas(List<BusinessAreas> businessAreas) {
			this.businessAreas = businessAreas;
		}
		public void setDirection(String direction) {
			this.direction = direction;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public void setStreet(String street) {
			this.street = street;
		}
	}
	
	
	List<Regeocode> regeocodes = new ArrayList<>();


	public List<Regeocode> getRegeocodes() {
		return regeocodes;
	}


	public void setRegeocodes(List<Regeocode> regeocodes) {
		this.regeocodes = regeocodes;
	}
}
