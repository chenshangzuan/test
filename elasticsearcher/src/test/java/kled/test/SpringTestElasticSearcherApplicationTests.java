/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kled.test;

import kled.test.es.domain.EsTest;
import kled.test.es.repository.EsTestRepository;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Tests for {@link ElasticSearcherApplication}.
 * 
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringTestElasticSearcherApplicationTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private EsTestRepository esTestRepository;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Test
	public void testContextLoads() throws Exception {
	}

	@Test
	public void esTestRepository(){
		System.out.println("queryByUser:" + esTestRepository.findByUser("kimchy"));
		//索引数据(写入)
		//System.out.println(esTestRepository.index(new EsTest()));
		//分页搜索数据
		QueryStringQueryBuilder queryBuilder1 = QueryBuilders.queryStringQuery("kimchy");
		Pageable pageable = PageRequest.of(0,10);
		Page<EsTest> page = esTestRepository.search(queryBuilder1, pageable);
		System.out.println(page);

//		QueryBuilder queryBuilder = QueryBuilders.moreLikeThisQuery("user")
//				.like("kimchy");

		IdsQueryBuilder queryBuilder2 = QueryBuilders.idsQuery().addIds("84M3k3MBnrYpIqUsBOIj");
		esTestRepository.search(queryBuilder2).forEach(x -> {
			if(x.getId().equals("84M3k3MBnrYpIqUsBOIj")){
				//searchSimilar 实现基于内容推荐，如何使用？
				System.out.println("search similar document");
				Page<EsTest> similarEsTestPage= esTestRepository.searchSimilar(x, new String[]{"user"},  PageRequest.of(1,10));
				System.out.println("search similar document size=" + similarEsTestPage.getContent().size());
			}
		});

		//原生复合搜索(高亮无效)
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(queryBuilder2).withHighlightFields(new HighlightBuilder.Field("user").preTags("<font color='#dd4b39'>")
				.postTags("</font>"));
		esTestRepository.search(nativeSearchQueryBuilder.build()).forEach(System.out::println);
	}

	@Test
	public void elasticTemplate(){
		GetQuery getQuery = new GetQuery();
		getQuery.setId("84M3k3MBnrYpIqUsBOIj");
		System.out.println(elasticsearchTemplate.queryForObject(getQuery, EsTest.class));

		//error: ElasticsearchParseException[Failed to derive xcontent
//		StringQuery stringQuery = new StringQuery("kimchy1");
//		System.out.println(elasticsearchTemplate.queryForObject(stringQuery, EsTest.class));

		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
				.withIndices("es_test")
				.withQuery(QueryBuilders.matchQuery("user", "kimchy"))
				//.withSort(SortBuilders.fieldSort("myId").order(SortOrder.DESC))
				.withPageable(PageRequest.of(0, 10))
				.build();
		AggregatedPage<EsTest> page = elasticsearchTemplate.queryForPage(nativeSearchQuery, EsTest.class);
		List<EsTest> esTestList = page.getContent();
		esTestList.forEach(item -> System.out.println(item.toString()));

		CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria()
				.and(new Criteria("user").is("kimchy1")))
				.setPageable(
				PageRequest.of(0, 10));
		Page<EsTest> pages = elasticsearchTemplate.queryForPage(criteriaQuery,
				EsTest.class);
		pages.forEach(item -> System.out.println(item.toString()));

	}

}
