package com.example.trixihomework.services;

import com.example.trixihomework.models.District;
import com.example.trixihomework.models.Town;
import com.example.trixihomework.repositories.DistrictRepository;
import com.example.trixihomework.repositories.TownRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipInputStream;

@Service
@AllArgsConstructor
public class XMLServiceImpl implements XMLService {
    private final TownRepository townRepository;
    private final DistrictRepository districtRepository;

    @Override
    public void download(URL url, String destinationPath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(destinationPath);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
    }

    @Override
    public void processFile(String path) throws IOException, XMLStreamException {
        List<District> districts = new LinkedList<>();
        Town town = null;
        District district = null;
        ZipInputStream zis = new ZipInputStream(new FileInputStream(path));
        zis.getNextEntry();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(zis);
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                StartElement element = nextEvent.asStartElement();
                String prefix = element.getName().getPrefix();
                String tag = element.getName().getLocalPart();
                String fullTag = prefix + ":" + tag;
                switch (fullTag) {
                    case "vf:Obec" -> town = new Town();
                    case "obi:Kod" -> {
                        nextEvent = reader.nextEvent();
                        if (town != null) town.setCode(Integer.parseInt(nextEvent.asCharacters().getData()));
                    }
                    case "obi:Nazev" -> {
                        nextEvent = reader.nextEvent();
                        if (town != null) town.setName(nextEvent.asCharacters().getData());
                    }
                    case "vf:CastObce" -> district = new District();
                    case "coi:Kod" -> {
                        nextEvent = reader.nextEvent();
                        if (district != null) district.setCode(Integer.parseInt(nextEvent.asCharacters().getData()));
                    }
                    case "coi:Nazev" -> {
                        nextEvent = reader.nextEvent();
                        if (district != null) district.setName(nextEvent.asCharacters().getData());
                    }
                }
            }
            if (nextEvent.isEndElement()) {
                EndElement element = nextEvent.asEndElement();
                String prefix = element.getName().getPrefix();
                String tag = element.getName().getLocalPart();
                String fullTag = prefix + ":" + tag;
                if ("vf:Obec".equals(fullTag)) {
                    townRepository.save(town);
                } else if ("vf:CastObce".equals(fullTag)) {
                    district.setTown(town);
                    districts.add(district);
                    districtRepository.save(district);
                }
            }
        }
        town = townRepository.findById(town.getCode())
                .orElseThrow(() -> new IllegalArgumentException("No such town found!"));
        town.setDistricts(districts);
        townRepository.save(town);
    }
}
