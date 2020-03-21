/*
 *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.alibaba.fescar.workshop.service.impl;

import com.alibaba.fescar.workshop.service.StorageService;

import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * The type Storage service.
 *
 * @author jimin.jm @alibaba-inc.com
 */
//@EnableAutoDataSourceProxy(useJdkProxy = true)
public class StorageServiceImpl implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);

    private JdbcTemplate jdbcTemplate;

    /**
     * Sets jdbc template.
     *
     * @param jdbcTemplate the jdbc template
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deduct(String commodityCode, int count) {
        LOGGER.info("Storage Service Begin ... xid: " + RootContext.getXID());
        LOGGER.info("Deducting inventory SQL: update STORAGE_TBL set count = count - {} where commodity_code = {}",
            count, commodityCode);

        jdbcTemplate.update("update storage_tbl set count = count - ? where commodity_code = ?",
            count, commodityCode);
        LOGGER.info("Storage Service End ... ");

        //test update
        /*
        final List<ArrayList> params = new ArrayList<>();
        for (int i = 1000; i < 1003; i++) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(i);
            arrayList.add("C"+i);
            arrayList.add(100);
            params.add(arrayList);
        }

        //update
        //String sql="update STORAGE_TBL set commodity_code=?, count=? where id=? and count=?";

        String sql="delete from STORAGE_TBL where id=? and count=?";

        try {
            int[] affRows = jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        //update
                        //ps.setString(1, params.get(i).get(1).toString());
                        //ps.setInt(2, Integer.parseInt(params.get(i).get(2).toString())+100);
                        //ps.setLong(3, Long.parseLong(params.get(i).get(0).toString()));
                        //ps.setInt(4, Integer.parseInt(params.get(i).get(2).toString()));

                        ps.setLong(1, Long.parseLong(params.get(i).get(0).toString()));
                        ps.setInt(2, Integer.parseInt(params.get(i).get(2).toString()));


                    }

                    @Override
                    public int getBatchSize() {
                        return params.size();
                    }
                });
            int affTotal = 0;
            for (int i : affRows) {
                affTotal += i;
            }
            Assert.isTrue(affTotal == params.size(), "xx");
        }catch (Exception exx){
            StringBuilder sb=new StringBuilder();
        }
        */

        // test inc batch
        /*
        jdbcTemplate.update("insert into STORAGE_TBL(commodity_code,count) values('fake',999),('fake1',999),('fake2',
        999)");
        */

        //jdbcTemplate.update("insert into STORAGE_TBL(commodity_code,count) values(?,?),(?,?),(?,?)",new
        // Object[]{"fake1",999,"fake2",999,"fake3",999});

        /* test no-inc batch
        //jdbcTemplate.update("insert into STORAGE_TBL(id,commodity_code,count) values(111,'fake',999),(112,'fake1',
        999),(113,'fake2',999)");

        //jdbcTemplate.update("insert into STORAGE_TBL(id,commodity_code,count) values(?,?,?),(?,?,?),(?,?,?)",new
        Object[]{111,"fake1",999,112,"fake2",999,113,"fake3",999});
        */

        //test batchUpdate
        /*
        final List<ArrayList> params = new ArrayList<>();
        for (int i = 100; i < 200; i++) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(i);
            arrayList.add("batch");
            arrayList.add(999);
            params.add(arrayList);
        }
        try {
            int[] affRows = jdbcTemplate.batchUpdate("insert into STORAGE_TBL(id,commodity_code,count) values(?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, Long.parseLong(params.get(i).get(0).toString()));
                        ps.setString(2, params.get(i).get(1).toString());
                        ps.setInt(3, Integer.parseInt(params.get(i).get(2).toString()));

                    }

                    @Override
                    public int getBatchSize() {
                        return params.size();
                    }
                });
            int affTotal = 0;
            for (int i : affRows) {
                affTotal += i;
            }
            Assert.isTrue(affTotal == params.size(), "xx");
        }catch (Exception exx){
            StringBuilder sb=new StringBuilder();
        }
        */
    }

}
