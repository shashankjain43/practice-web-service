package com.snapdeal.ims.dao.impl;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Optional;
import com.snapdeal.ims.dao.IUserLockDao;
import com.snapdeal.ims.dbmapper.IUserLockMapper;
import com.snapdeal.ims.dbmapper.entity.UserLockDetails;

@ContextConfiguration("classpath:spring/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserLockDaoImplTest {
	@Autowired
	private IUserLockMapper userLockDetailsMapper;

	@Autowired
	private IUserLockDao userLockDao;

	private static final String STATUS_LOCKED = "LOCKED";
	private static final String STATUS_UNLOCKED = "UNLOCKED";
	private static long userID = 1;

	@org.junit.Test
	public void Test() {
		System.out.println("Begin ");
	}

	@org.junit.Test
	public void lockUserDetailsTest() {

		UserLockDetails expected = new UserLockDetails();
		expected.setCreatedTime(new Date());
		expected.setExpiryTime(addHours(new Date(), 1));
		expected.setLoginAttempts(3);
		expected.setStatus(STATUS_LOCKED);
		expected.setUserId(String.valueOf(++userID));

		userLockDao.lockUserEntry(expected);

		Optional<UserLockDetails> actual = userLockDao.getLockUserEntry(String
				.valueOf(userID));

		Assert.assertTrue(isEquals(expected, actual.get()));
		// System.out.println(actual.get());

	}

	@org.junit.Test
	public void unLockUserTest() {
		UserLockDetails expected = new UserLockDetails();
		expected.setCreatedTime(new Date());
		expected.setExpiryTime(addHours(new Date(), 1));
		expected.setLoginAttempts(3);
		expected.setStatus(STATUS_LOCKED);
		expected.setUserId(String.valueOf(++userID));

		userLockDao.lockUserEntry(expected);

		Optional<UserLockDetails> actual = userLockDao.getLockUserEntry(String
				.valueOf(userID));

		Assert.assertTrue(actual.isPresent());

		userLockDao.unLockUser(String.valueOf(userID));

		actual = userLockDao.getLockUserEntry(String.valueOf(userID));

		Assert.assertFalse(actual.isPresent());

		/*
		 * printUserLockDetails(actual.get());
		 * Assert.assertFalse(expected.getStatus()
		 * .equals(actual.get().getStatus()));
		 * Assert.assertTrue(actual.get().getStatus().equals(STATUS_UNLOCKED));
		 */
	}

	/*
	 * @org.junit.Test public void unLockUserFailTest() { String userId =
	 * "NOT PRESENT"; userLockDao.unLockUser(userId);
	 * 
	 * }
	 */

	@org.junit.Test
	public void updateLockUserEntry() {
		UserLockDetails expected = new UserLockDetails();
		expected.setCreatedTime(new Date());
		expected.setExpiryTime(addHours(new Date(), 1));
		expected.setLoginAttempts(3);
		expected.setStatus(STATUS_LOCKED);
		expected.setUserId(String.valueOf(++userID));

		userLockDao.lockUserEntry(expected);

		Optional<UserLockDetails> actual = userLockDao.getLockUserEntry(String
				.valueOf(userID));

		Assert.assertTrue(isEquals(expected, actual.get()));
		expected.setLoginAttempts(10);

		userLockDao.updateLockUserEntry(expected);

		actual = userLockDao.getLockUserEntry(String.valueOf(userID));

		// printUserLockDetails(actual.get());

		Assert.assertTrue(actual.get().getLoginAttempts() == 10);

	}

	@org.junit.Test
	public void getLockUserEntryTest() {
		UserLockDetails expected = new UserLockDetails();
		expected.setCreatedTime(new Date());
		expected.setExpiryTime(addHours(new Date(), 1));
		expected.setLoginAttempts(3);
		expected.setStatus(STATUS_LOCKED);
		expected.setUserId(String.valueOf(++userID));

		userLockDao.lockUserEntry(expected);

		Optional<UserLockDetails> actual = userLockDao.getLockUserEntry(String
				.valueOf(userID));

		Assert.assertTrue(isEquals(expected, actual.get()));

	}

	public static Date addHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours); // minus number would decrement the days
		return cal.getTime();
	}

	public boolean isEquals(UserLockDetails expected, UserLockDetails actual) {
		boolean isEqual = false;

		if (expected.getStatus().equals(actual.getStatus())
				&& expected.getLoginAttempts() == actual.getLoginAttempts()
				&& expected.getCreatedTime().equals(actual.getCreatedTime())
				&& expected.getExpiryTime().equals(actual.getExpiryTime())) {
			isEqual = true;
		}

		return isEqual;

	}

	public void printUserLockDetails(UserLockDetails test) {
		System.out.println(test.getLoginAttempts() + " " + test.getStatus()
				+ " " + test.getExpiryTime() + " " + test.getCreatedTime()
				+ " " + test.getUserId());

	}
}
