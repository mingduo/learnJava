package otter.node.extend;


import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : weizc
 * @since 2021/1/15
 */
public class TestMain {


    public static void main(String[] args) throws IOException {

        System.out.println(System.getProperty("user.dir"));

        Path fiancialDataPath = Paths.get("learn-datax/src/main/resources/data/财务.txt");
        Path purcharseDataPath = Paths.get("learn-datax/src/main/resources/data/采购退.txt");
        List<OutlayData> outlayData = OutlayData.loadData(fiancialDataPath);
        List<PurcharseData> purcharseData = PurcharseData.loadData(purcharseDataPath);

        System.out.printf("outlayData size:%d purcharseData size :%d \n",
                outlayData.size(), purcharseData.size());

        outlayData.stream().map(t -> t.findOrgId(purcharseData))
                .filter(Objects::nonNull)
                .forEach(OutlayData::generalUpdateSql);

    }


    static class OutlayData {
        String pk_id;
        String id_source_bill;
        String id_own_org;


        static List<OutlayData> loadData(Path path) throws IOException {

            return Files.readAllLines(path).stream()
                    .map(l -> l.split(","))
                    .filter(line -> StringUtils.isNumeric(line[0]))
                    .map(line -> {
                        OutlayData outlayData = new OutlayData();
                        outlayData.pk_id = line[0];
                        outlayData.id_source_bill = line[1];
                        outlayData.id_own_org = line[2];
                        return outlayData;
                    }).collect(Collectors.toList());
        }

        OutlayData findOrgId(List<PurcharseData> that) {
            return that.stream().filter(t -> Objects.equals(t.pk_id, id_source_bill))
                    .filter(t -> !Objects.equals(t.id_own_org, id_own_org))
                    .map(t -> {
                     /*   System.out.printf("查询出财务数据 pk_id:%s,id_source_bill:%s," +
                                        "id_own_org:%s ,id_own_org(采购测):%s %n",
                                pk_id, id_source_bill,id_own_org,
                                t.id_own_org);*/
                        this.id_own_org = t.id_own_org;
                        return this;
                    })
                    .findFirst().orElse(null);
        }

        void generalUpdateSql() {
            String outLaySql = "update  tf_outlay " +
                    " set id_own_org='%s' " +
                    " where pk_id='%s'; \n";
            String outLayDetailSql = "update  tf_outlay_detail set id_own_org='%s' where id_payment='%s'; \n";

           System.out.printf(outLaySql, this.id_own_org, this.pk_id);
            System.out.printf(outLayDetailSql, this.id_own_org, this.pk_id);

        }
    }

    //pk_id = outlayData.id_source_bill

    static class PurcharseData {
        String pk_id;
        String id_own_org;

        static List<PurcharseData> loadData(Path path) throws IOException {

            return Files.readAllLines(path).stream()
                    .map(l -> l.split(","))
                    .filter(line -> StringUtils.isNumeric(line[0]))
                    .map(line -> {
                        PurcharseData data = new PurcharseData();
                        data.pk_id = line[0];
                        data.id_own_org = line[1];
                        return data;
                    }).collect(Collectors.toList());

        }
    }



}


