package com.snapdeal.opspanel.bulk.configuration;

import java.io.IOException;
import java.io.Writer;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.snapdeal.opspanel.batch.model.BulkRefundInputCSVModel;
import com.snapdeal.opspanel.batch.model.BulkRefundOutputCSVModel;
import com.snapdeal.opspanel.batch.utils.BatchUtils;
import com.snapdeal.opspanel.bulk.processors.BulkRefundBatchProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	PlatformTransactionManager txnManager;

	@Autowired
	DataSource dataSource;

	@Bean
	@StepScope
	public FlatFileItemReader<BulkRefundInputCSVModel> genericReconStatementReader(
			@Value("#{jobParameters[pathToFile]}") String pathToFile) {

		FlatFileItemReader<BulkRefundInputCSVModel> reader = new FlatFileItemReader<BulkRefundInputCSVModel>();
		reader.setLinesToSkip(1);
		reader.setResource(new FileSystemResource(pathToFile));
		reader.setLineMapper(new DefaultLineMapper<BulkRefundInputCSVModel>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(BatchUtils.bulkRefundColumns);
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<BulkRefundInputCSVModel>() {
					{
						setTargetType(BulkRefundInputCSVModel.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	@StepScope
	public ItemProcessor<BulkRefundInputCSVModel, BulkRefundOutputCSVModel> getRefundProcessor(
			@Value("#{jobParameters['refundKeyParam']}") String refundKey,
			@Value("#{jobParameters['merchantId']}") String merchantId) {
		// return implementation of Item Processor
		BulkRefundBatchProcessor processor = new BulkRefundBatchProcessor(refundKey, merchantId);

		return (ItemProcessor<BulkRefundInputCSVModel, BulkRefundOutputCSVModel>) processor;
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<BulkRefundOutputCSVModel> inputEntityWriter(
			@Value("#{jobParameters[pathToOutputFile]}") String pathToFile) {

		FlatFileItemWriter writer = new FlatFileItemWriter();
		writer.setResource(new FileSystemResource(pathToFile));
		DelimitedLineAggregator<BulkRefundOutputCSVModel> delLineAgg = new DelimitedLineAggregator<BulkRefundOutputCSVModel>();
		delLineAgg.setDelimiter(",");

		BeanWrapperFieldExtractor<BulkRefundOutputCSVModel> fieldExtractor = new BeanWrapperFieldExtractor<BulkRefundOutputCSVModel>();
		fieldExtractor.setNames(BatchUtils.bulkOutputRefundColumns);
		delLineAgg.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(delLineAgg);
		writer.setHeaderCallback(new FlatFileHeaderCallback() {

			public void writeHeader(Writer wr) throws IOException {

				wr.write("id, amount,comments,platformId,feeReversalCode,status,message");
			}
		});
		return writer;

	}

	@Bean(name = "bulkStep")
	public Step genericRefundStep(StepBuilderFactory stepBuilderFactory, ItemReader<BulkRefundInputCSVModel> reader,
			ItemWriter<BulkRefundOutputCSVModel> writer,
			ItemProcessor<BulkRefundInputCSVModel, BulkRefundOutputCSVModel> processor) {
		return stepBuilderFactory.get("RefundStep").<BulkRefundInputCSVModel, BulkRefundOutputCSVModel> chunk(50)
				.reader(reader).processor(processor).writer(writer).build();

		/*
		 * .throttleLimit(15) .taskExecutor(new
		 * SimpleAsyncTaskExecutor()).listener(bulkRefundListener)
		 */
	}

	@Bean
	public JobRepositoryFactoryBean makeRepositoryyBean() {

		JobRepositoryFactoryBean bean = new JobRepositoryFactoryBean();
		bean.setDataSource(dataSource);
		bean.setTransactionManager(txnManager);
		return bean;
	}

}
