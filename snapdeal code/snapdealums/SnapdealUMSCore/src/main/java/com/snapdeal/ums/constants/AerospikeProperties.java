/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snapdeal.ums.constants;

/**
 * @version 1.0, Jan 2, 2015
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 */

public class AerospikeProperties {

	public enum ClientPolicy {
		// Client policy properties
		SOCKET_IDLE("aerospike.maxSocketIdle"), MAX_THREADS(
				"aerospike.maxThreads"), SHARED_THREADS(
				"aerospike.sharedThreadPool"), CONNECTION_TIMEOUT(
				"aerospike.connection.timeout");

		private String value;

		ClientPolicy(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum ReadPolicy {
		// Default read policy properties
		DEFAULT_MAX_READ_RETRIES("aerospike.default.read.maxRetries"), DEFAULT_SLEEP_BETWEEN_READ_RETRIES(
				"aerospike.default.read.sleepBetweenRetries"), DEFAULT_READ_TIMEOUT(
				"'aerospike.default.read.timeout");

		private String value;

		ReadPolicy(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum Namespace {
		USER_NAMESPACE("BasicUserSpace");

		private String value;

		Namespace(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

	}

	public enum Set {
		USER_SET("BasicUserData"), USER_ID_EMAIL_MAP("UserIdEmailMap");

		private String value;

		Set(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum Bin {
		BASIC_USER_DATA_BIN("UserSRO"), USER_ID_EMAIL_BIN("Email");

		private String value;

		Bin(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}
}