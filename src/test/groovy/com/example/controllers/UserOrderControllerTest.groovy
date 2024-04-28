package com.example.controllers

import com.example.models.Order
import com.example.services.IDynamoDBFacadeService
import com.example.services.OrderService
import spock.lang.Specification

import java.security.Principal
import java.time.Instant

class UserOrderControllerTest extends Specification {
    def "ListOrders"() {
        given:
        IDynamoDBFacadeService dynamoDBFacadeService = Mock(IDynamoDBFacadeService)
        OrderService orderService = new OrderService(dynamoDBFacadeService, null, null)
        UserOrderController userOrderController = new UserOrderController(orderService)
        Principal principal = Mock(Principal)
        principal.getName() >> "d84e61c73fe7-de8f-47ed-833a-797b001f"

        when:
        List<Order> orderList = userOrderController.listOrders(principal)

        then:
        1 * dynamoDBFacadeService.query(Order.class, _) >> {
            List.of(new Order(mealId: "797b001f-de8f-47ed-833a-d84e61c73fe7", dateOfMeal: Instant.ofEpochSecond(1711487392), uid: "d84e61c73fe7-de8f-47ed-833a-797b001f"))
        }
        orderList.get(0) == new Order(mealId: "797b001f-de8f-47ed-833a-d84e61c73fe7", dateOfMeal: Instant.ofEpochSecond(1711487392), uid: "d84e61c73fe7-de8f-47ed-833a-797b001f")
    }
}
