package com.oracle.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

/*
        <country lang="ru">Россия</country>
        <company-id>gazprombank_445774</company-id>
        <address lang="ru">Астраханская область, Астрахань, ул. Адмирала Нахимова 60В, ГК "Золотой Затон"</address>
        <url>https://www.open.ru</url>
        <coordinates>
            <lat>46.30717</lat>
            <lon>47.98729</lon>
        </coordinates>
        <actualization-date>21.07.2020</actualization-date>
        <feature-boolean name="public" value="1"/>
        <feature-boolean name="cash_in" value="0"/>
        <name lang="ru">Банкомат Газпромбанк</name>
        <working-time>Круглосуточно</working-time>
        <rubric-id>184105402</rubric-id>

После скачивания xml из open.ru все сущности выглядят как указано выше
Необходимо изменить формат данных (ctrl+R) следующим образом:
 lang="ru" на ""
<feature-boolean name="public" value="1"/> = <feature-boolean_public>1</feature-boolean_public>
<feature-boolean name="public" value="0"/> = <feature-boolean_public>0</feature-boolean_public>
<feature-boolean name="cash_in" value="1"/> = <feature-boolean_cash>1</feature-boolean_cash>
<feature-boolean name="cash_in" value="0"/> = <feature-boolean_cash>0</feature-boolean_cash>

скрипт это делает и пишет каждую сущность в виде запроса insert в выходной файл
 */


@JacksonXmlRootElement(localName = "companies")
class Companies implements Serializable {

    @JacksonXmlProperty(localName = "company")
    @JacksonXmlCData
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<Company> companies;

    @Override
    public String toString() {
        return "CompaniesA{" +
                "companies=" + companies +
                '}';
    }

    static class Company{

    public String country;
        @JsonProperty("company-id")
        public String company_id;
        public String address;
        public String url;

        @JsonProperty("coordinates")
        public Coordinates coordinates;

        @JsonProperty("actualization-date")
        public String actualization_date;

        @JsonProperty("feature-boolean_public")
        public String feature_boolean_public;
        @JsonProperty("feature-boolean_cash")
        public String feature_boolean_cash;
        public String name;
        @JsonProperty("working-time")
        public String working_time;
        @JsonProperty("rubric-id")
        public String rubric_id;


        @Override
        public String toString() {
            return "Company{" +
                    "country='" + country + '\'' +
                    ", company_id='" + company_id + '\'' +
                    ", address='" + address + '\'' +
                    ", url='" + url + '\'' +
                    ", coordinates=" + coordinates +
                    ", actualization_date='" + actualization_date + '\'' +
                    ", feature_boolean_public='" + feature_boolean_public + '\'' +
                    ", feature_boolean_cash='" + feature_boolean_cash + '\'' +
                    ", name='" + name + '\'' +
                    ", working_time='" + working_time + '\'' +
                    ", rubric_id='" + rubric_id + '\'' +
                    '}';
        }

        public static class Coordinates{
            public String lat;
            public String lon;

            public Coordinates(String lat, String lon) {
                this.lat = lat;
                this.lon = lon;
            }

            public Coordinates() {
            }

            @Override
            public String toString() {
                return "Coordinates{" +
                        "lat='" + lat + '\'' +
                        ", lon='" + lon + '\'' +
                        '}';
            }
        }
    }

    public static void main(String[] args) throws IOException {
//    String xml = "<company>\n" +
//            "        <country lang=\"ru\">Россия</country>\n" +
//            "        <company-id>gazprombank_461758</company-id>\n" +
//            "        <address lang=\"ru\">Астраханская область, Астрахань, ул. Кирова 7, ТЦ \"ЦУМ\"</address>\n" +
//            "        <url>https://www.open.ru</url>\n" +
//            "        <coordinates>\n" +
//            "            <lat>46.35266</lat>\n" +
//            "            <lon>48.03828</lon>\n" +
//            "        </coordinates>\n" +
//            "        <actualization-date>21.07.2020</actualization-date>\n" +
//            "        <feature-boolean name=\"public\" value=\"1\"/>\n" +
//            "        <feature-boolean name=\"cash_in\" value=\"0\"/>\n" +
//            "        <name lang=\"ru\">Банкомат Газпромбанк</name>\n" +
//            "        <working-time>ежедневно 9:00-19:00</working-time>\n" +
//            "        <rubric-id>184105402</rubric-id>\n" +
//            "    </company>";

    String xml = "<companies>\n" +
            "    <company>\n" +
            "        <country>Россия</country>\n" +
            "        <company-id>gazprombank_461758</company-id>\n" +
            "        <address>Астраханская область, Астрахань, ул. Кирова 7, ТЦ \"ЦУМ\"</address>\n" +
            "        <url>https://www.open.ru</url>\n" +
            "        <coordinates>\n" +
            "            <lat>46.35266</lat>\n" +
            "            <lon>48.03828</lon>\n" +
            "        </coordinates>\n" +
            "        <actualization-date>21.07.2020</actualization-date>\n" +
            "        <name>Банкомат Газпромбанк</name>\n" +
            "        <working-time>ежедневно 9:00-19:00</working-time>\n" +
            "        <rubric-id>184105402</rubric-id>\n" +
            "    </company>\n" +
            "    <company>\n" +
            "        <country>Россия</country>\n" +
            "        <company-id>gazprombank_445774</company-id>\n" +
            "        <address>Астраханская область, Астрахань, ул. Адмирала Нахимова 60В, ГК \"Золотой Затон\"</address>\n" +
            "        <url>https://www.open.ru</url>\n" +
            "        <coordinates>\n" +
            "            <lat>46.30717</lat>\n" +
            "            <lon>47.98729</lon>\n" +
            "        </coordinates>\n" +
            "        <actualization-date>21.07.2020</actualization-date>\n" +
            "        <name>Банкомат Газпромбанк</name>\n" +
            "        <working-time>Круглосуточно</working-time>\n" +
            "        <rubric-id>184105402</rubric-id>\n" +
            "    </company>\n" +
            "</companies>";
//    File file = ResourceUtils.getFile("/Users/ar11/Downloads/db/src/main/resources/partnerX.xml");
    File file = new File("/Users/ar11/Downloads/dbx/partnerInitial.xml");
    String content = new String(Files.readAllBytes(file.toPath()));

    System.out.println(content);
    String newContent = content
            .replaceAll(" lang=\"ru\"", "")
            .replaceAll("<feature-boolean name=\"public\" value=\"1\"/>", "<feature-boolean_public>1</feature-boolean_public>")
            .replaceAll("<feature-boolean name=\"public\" value=\"0\"/>", "<feature-boolean_public>0</feature-boolean_public>")
            .replaceAll("<feature-boolean name=\"cash_in\" value=\"1\"/>", "<feature-boolean_cash>1</feature-boolean_cash>")
            .replaceAll("<feature-boolean name=\"cash_in\" value=\"0\"/>", "<feature-boolean_cash>0</feature-boolean_cash>")
            .replaceAll("'", " ");
    XmlMapper mapper = new XmlMapper();
    Companies value = mapper.readValue(newContent, Companies.class);
    System.out.println(value.companies.size());

        /*
        Сделаем sql-ник

         */
        String template = "insert into go_geo_obj_atm_partner(country, company_id, address, url, latitude, longtitude, actualization_date, feature_public, feature_cash_in, name, working_time, rubric_id)\n" +
                "values ('','','','','','','','','','','','');";

        List<Company> list = value.companies;

        FileWriter writer = new FileWriter(args[1] == null ? "output.txt" : args[1]);
        for (int i = 0; i < list.size(); i++) {
            Company company = list.get(i);
            String COUNTRY = company.country;
            String COMPANY_ID = company.company_id;
            String ADDRESS = company.address;
            String url = company.url;
            String output = String.format("insert into go_geo_obj_atm_partner1(country, company_id, address, url, latitude, longtitude, actualization_date, feature_public, feature_cash_in, name, working_time, rubric_id) " +
                            "values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s'); \n",
                    COUNTRY,
                    COMPANY_ID,
                    ADDRESS,
                    url,
                    company.coordinates.lat,
                    company.coordinates.lon,
                    company.actualization_date,
                    company.feature_boolean_public,
                    company.feature_boolean_cash,
                    company.name,
                    company.working_time,
                    company.rubric_id);


            writer.write(output);
            if (i % 800 == 0) {
                writer.write("commit;\n");
            }
        }
        writer.close();
}
}

