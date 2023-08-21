package ru.ceki.fgiski2.eventbot;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.springframework.stereotype.Service;
import ru.ceki.fgiski2.dto.EventDto;

@Service
public class EventBotLogicImpl implements EventBotLogic {
    @Override
    public EventDto buildEventDto(String text) {
        Collection<String> tags = new HashSet<>();
        Pattern pattern = Pattern.compile("#[_0-9a-zA-Zа-яА-Я]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String tag = matcher.group();
            if (tag.length() > 1) {
                tags.add(tag.substring(1));
            }
        }
        for (String tag : tags) {
            text = text.replaceAll('#' + tag, "");
        }
        return new EventDto(text, tags);
    }
}