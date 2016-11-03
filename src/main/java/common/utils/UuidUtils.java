package common.utils;

import java.util.UUID;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

public class UuidUtils {

	private static final EthernetAddress ETHERNET_ADDR = EthernetAddress.fromInterface();
	private static final TimeBasedGenerator uuidTimeAdrBasedGenerator = Generators.timeBasedGenerator(ETHERNET_ADDR);

	public static UUID generateType1() {
		return uuidTimeAdrBasedGenerator.generate();
	}

}
