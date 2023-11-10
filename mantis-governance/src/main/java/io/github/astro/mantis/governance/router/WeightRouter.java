package io.github.astro.mantis.governance.router;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceProvider;

import java.util.Comparator;
import java.util.List;

import static io.github.astro.mantis.common.constant.KeyValues.Router.WEIGHT;

@ServiceProvider(WEIGHT)
public class WeightRouter implements Router {

    @Override
    public List<URL> route(List<URL> urls, CallData callData) {
        List<URL> weightUrls = urls.stream().sorted(Comparator.comparing(url -> url.getParameter(Key.WEIGHT))).toList();
        String maxWeight = weightUrls.get(weightUrls.size() - 1).getParameter(Key.WEIGHT);
        return urls.stream().filter(url -> convertCompare(url.getParameter(Key.WEIGHT), maxWeight)).toList();
    }

    private boolean convertCompare(String str1, String str2) {
        return Integer.parseInt(str1) == Integer.parseInt(str2);
    }

}
