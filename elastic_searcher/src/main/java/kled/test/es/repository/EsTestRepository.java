/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.es.repository;

import kled.test.es.domain.EsTest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsTestRepository extends ElasticsearchRepository<EsTest, String> {

    List<EsTest> findByUser(String userName);
}
