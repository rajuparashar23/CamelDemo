/**
 * 
 */
package com.camel.demo.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * @author rajup
 *
 */

@Component
public class DemoRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("file:D:/demo/input?fileName=employee.txt")
			.routeId("Route-1")
			.split(bodyAs(String.class).tokenize(System.lineSeparator()))
				.to("activemq:queue:EMPLOYEE");
		
		from("activemq:queue:EMPLOYEE")
			.routeId("Route-2")
			.filter(bodyAs(String.class).not().startsWith("DUMMY"))
				.to("activemq:queue:EMPLOYEE_FILTERED");
		
		from("activemq:queue:EMPLOYEE_FILTERED")
			.routeId("Route-3")
			.choice()
				.when(bodyAs(String.class).startsWith("JP"))
					.to("activemq:queue:EMPLOYEE_JP")
				.otherwise()
					.to("activemq:queue:EMPLOYEE_GLOBAL");
			
			
				
	}

}
