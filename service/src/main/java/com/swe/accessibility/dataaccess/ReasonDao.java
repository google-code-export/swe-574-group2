package com.swe.accessibility.dataaccess;

import java.util.List;

import com.swe.accessibility.domain.Reason;
import com.swe.accessibility.domain.SubReason;


public interface ReasonDao {
	
	/**
	 * Insert reason object
	 * @param reason
	 * @return
	 */
	int insert(Reason reason);
	
	/**
	 * updates reason object
	 * @param reason
	 */
	void update(Reason reason);
	
	/**
	 * Deletes reason object
	 * @param reason
	 */
	void delete(Reason reason);
	
	/**
	 * Get sub reasons by main reason id
	 * @param id ID of the main reason
	 * @return
	 */
	SubReason getById(int id);

	/**
	 * Get main reasons
	 * @return
	 */
	List<Reason> getMainAll();
	
	/**
	 * Get sub reasons
	 * @param id
	 * @return
	 */
	List<SubReason> getSubAll(int id);

	SubReason getEntriesBySub(int id);

}
