/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ticket_app.demo.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Calendar;

import com.ticket_app.demo.repository.TicketsRepository;
import com.ticket_app.demo.repository.helper.TicketSpec;
import com.ticket_app.demo.repository.helper.SearchCriteria;
import com.ticket_app.demo.repository.helper.SearchOperation;
import com.ticket_app.demo.model.TicketsEntity;


@Component
public class ExpiryScheduler {

    @Autowired
    private TicketsRepository ticket_repository;

	private static final Logger log = LoggerFactory.getLogger(ExpiryScheduler.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 900000)
	public void reportCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        long t = calendar.getTimeInMillis();
        Pageable paging = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("id"));
        TicketSpec ticketFilter = new TicketSpec();
        ticketFilter.add(new SearchCriteria("status",TicketsEntity.Status.CUSTOMER_RESPONDED, SearchOperation.IN));
        Page<TicketsEntity> ticketsList = ticket_repository.findAll(ticketFilter,paging);
        if(ticketsList.hasContent()) {
            for(TicketsEntity ticket:ticketsList){
                long diff = t - ticket.getUpdated();
                long val = 2592000000L;
                if( diff >= val){
                    ticket.setStatus(TicketsEntity.Status.CLOSED);
                    ticket_repository.save(ticket);
                }
            }
        }
	}
}