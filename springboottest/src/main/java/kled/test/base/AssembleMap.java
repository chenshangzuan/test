/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kled
 * @version $Id: AssembleMap.java, v 0.1 2019-06-13 17:33:57 kled Exp $
 */
public class AssembleMap {

    public static void main(String[] args) throws IOException {
        String city = IOUtils.toString(new ClassPathResource("city.json").getInputStream(), "UTF-8");

        String cityPosition1 = IOUtils.toString(new ClassPathResource("citiesGeo.json").getInputStream(), "UTF-8");
        String cityPosition2 = IOUtils.toString(new ClassPathResource("citiesAbroad.json").getInputStream(), "UTF-8");
        List<CityPosition> cityPositions1 = JSON.parseArray(cityPosition1, CityPosition.class);

        List<CityPosition> cityPositions2 = JSON.parseArray(cityPosition2, CityPosition.class);
        cityPositions2.forEach(x -> x.setAbroad(true));

        cityPositions1.addAll(cityPositions2);
        System.out.println(cityPositions1);
        Map<String, CityPosition> positionMap = cityPositions1.stream().collect(Collectors.toMap(CityPosition::getName, x -> x));

        List<City> cityList = Lists.newArrayList();
        JSONObject jsonObject = (JSONObject) JSON.parse(city);
        for (String abroadame : jsonObject.keySet()) {
            CityPosition cityPosition = positionMap.get(abroadame);
            cityList.add(new City(jsonObject.getString(abroadame), abroadame, cityPosition.getCoord().get(0),
                    cityPosition.getCoord().get(1), cityPosition.isAbroad));
        }

        //System.out.println(city);
        System.out.println(cityList);
        FileUtils.writeByteArrayToFile( new ClassPathResource("city_info.json").getFile(), JSON.toJSONString(cityList).getBytes("UTF-8"));
    }

    public static class City {
        private String name;
        private String abroadName;
        private Float  longitude;
        private Float  latitude;
        private boolean isAbroad;

        public City(String name, String abroadName, Float longitude, Float latitude, boolean isAbroad) {
            this.name = name;
            this.abroadName = abroadName;
            this.longitude = longitude;
            this.latitude = latitude;
            this.isAbroad = isAbroad;
        }

        public boolean isAbroad() {
            return isAbroad;
        }

        public void setAbroad(boolean abroad) {
            isAbroad = abroad;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAbroadName() {
            return abroadName;
        }

        public void setAbroadName(String abroadName) {
            this.abroadName = abroadName;
        }

        public Float getLongitude() {
            return longitude;
        }

        public void setLongitude(Float longitude) {
            this.longitude = longitude;
        }

        public Float getLatitude() {
            return latitude;
        }

        public void setLatitude(Float latitude) {
            this.latitude = latitude;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("City [");
            sb.append("name=").append(name);
            sb.append(", abroadName=").append(abroadName);
            sb.append(", longitude=").append(longitude);
            sb.append(", latitude=").append(latitude);
            sb.append(", isAbroad=").append(isAbroad);
            sb.append(']');
            return sb.toString();
        }
    }

    public static class CityPosition {
        private boolean isAbroad;
        private String      name;
        private List<Float> coord;

        public boolean isAbroad() {
            return isAbroad;
        }

        public void setAbroad(boolean abroad) {
            isAbroad = abroad;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Float> getCoord() {
            return coord;
        }

        public void setCoord(List<Float> coord) {
            this.coord = coord;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("CityPosition [");
            sb.append("isAbroad=").append(isAbroad);
            sb.append(", name=").append(name);
            sb.append(", coord=").append(coord);
            sb.append(']');
            return sb.toString();
        }
    }
}
