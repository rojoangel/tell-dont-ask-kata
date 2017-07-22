package it.gabrieletondi.telldontaskkata.useCase.request;

import java.util.ArrayList;
import java.util.List;

public class SellItemsRequest {
    private List<SellItemRequest> requests;

    public SellItemsRequest() {
        requests = new ArrayList<>();
    }

    public void add(SellItemRequest request) {
        requests.add(request);
    }

    public List<SellItemRequest> getRequests() {
        return requests;
    }
}
