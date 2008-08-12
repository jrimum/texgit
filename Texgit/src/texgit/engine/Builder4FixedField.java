package texgit.engine;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang.StringUtils.countMatches;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import texgit.TexgitException;
import texgit.language.EnumFormats;
import texgit.language.EnumFormatsTypes;
import texgit.language.MetaField;
import texgit.type.component.EnumCommonFiller;
import texgit.type.component.EnumSide;
import texgit.type.component.Filler;
import texgit.type.component.FixedField;
import br.com.nordestefomento.jrimum.utilix.Util4Date;

class Builder4FixedField {

	static FixedField<?> build(MetaField metaField) {

		FixedField<?> fixedField = null;

		try {

			fixedField = getInstance(metaField);

		} catch (ParseException e) {
			throw new TexgitException("Field: " + metaField.getName(), e);
		}

		return fixedField;
	}

	private static FixedField<?> getInstance(MetaField metaField)
			throws ParseException {

		FixedField<?> fField = null;

		Format formatter = getFormater(metaField);

		switch (metaField.getType()) {

		case STRING:
			FixedField<String> fSTR = new FixedField<String>();
			if (isNotNull(metaField.getValue()))
				fSTR.setValue(metaField.getValue());
			else
				fSTR.setValue(EMPTY);
			fField = fSTR;
			break;
		case INTEGER:
			FixedField<Integer> fINT = new FixedField<Integer>();
			if (isNotNull(metaField.getValue()))
				fINT.setValue(Integer.parseInt(metaField.getValue()));
			else
				fINT.setValue(new Integer(0));
			fField = fINT;
			break;
		case LONG:
			FixedField<Long> fLNG = new FixedField<Long>();
			if (isNotNull(metaField.getValue()))
				fLNG.setValue(Long.parseLong(metaField.getValue()));
			else
				fLNG.setValue(new Long(0));
			fField = fLNG;
			break;
		case FLOAT:
			FixedField<Float> fFLT = new FixedField<Float>();
			if (isNotNull(metaField.getValue()))
				fFLT.setValue(Float.parseFloat(metaField.getValue()));
			else
				fFLT.setValue(new Float(0));
			fField = fFLT;
			break;
		case DOUBLE:
			FixedField<Double> fDBE = new FixedField<Double>();
			if (isNotNull(metaField.getValue()))
				fDBE.setValue(Double.parseDouble(metaField.getValue()));
			else
				fDBE.setValue(new Double(0));
			fField = fDBE;
			break;
		case BIGDECIMAL:
			FixedField<BigDecimal> fBDL = new FixedField<BigDecimal>();
			if (isNotNull(metaField.getValue()))
				fBDL.setValue(new BigDecimal(DecimalFormat.class
						.cast(formatter).parse(metaField.getValue())
						.doubleValue()));
			else
				fBDL.setValue(new BigDecimal(0));
			fField = fBDL;
			break;
		case DATE:
			FixedField<Date> fDTE = new FixedField<Date>();
			if (isNotNull(metaField.getValue()))
				fDTE.setValue(DateFormat.class.cast(formatter).parse(
						metaField.getValue()));
			else
				fDTE.setValue(Util4Date.DATE_NULL);
			fField = fDTE;
			break;
		}

		fField.setName(metaField.getName());
		fField.setFixedLength(metaField.getLength());
		fField.setFiller(getFiller(metaField));
		fField.setFormatter(formatter);
		fField.setBlankAccepted(metaField.isBlankAccepted());

		return fField;
	}

	private static Filler<?> getFiller(MetaField metaField) {

		Filler<?> filler = null;

		if (isNotNull(metaField.getFiller())) {

			Filler<String> filr = new Filler<String>();
			filr.setPadding(metaField.getFiller().getPadding());
			filr.setSideToFill(EnumSide.valueOf(metaField.getFiller()
					.getSideToFill().name()));
			filler = filr;

		} else {
			filler = EnumCommonFiller.valueOf(metaField.getPadding().name())
					.getOne();
		}

		return filler;
	}

	private static Format getFormater(MetaField metaField) {

		Format formatter = null;

		if (isNotNull(metaField.getFormatter())) {

			formatter = buildFormat(metaField.getFormatter().getFormat(),
					metaField.getFormatter().getType());

		} else {

			EnumFormats format = metaField.getFormat();

			EnumFormatsTypes type = EnumFormatsTypes.valueOf(format.name()
					.split("_")[0]);

			formatter = buildFormat(buildFormat(format, type), type);
		}

		return formatter;
	}

	private static Format buildFormat(String strFormat, EnumFormatsTypes type) {

		Format format = null;

		switch (type) {

		case DATE:
			format = new SimpleDateFormat(strFormat);
			break;
		case DECIMAL:
			format = new DecimalFormat(strFormat);
			break;
		}

		return format;
	}

	private static String buildFormat(EnumFormats format, EnumFormatsTypes type) {

		final String BASE_DECIMAL_FORMAT = "0.";

		String strFormat = EMPTY;
		/*
		 * DATE_DDMMYY, DATE_DDMMYYYY, DATE_YYMMDD, DATE_YYYYMMDD,
		 * DECIMAL_D,DECIMAL_DD, DECIMAL_DDD, DECIMAL_DDDD;
		 */
		String defFormat = format.name().split("_")[1];

		switch (type) {
		case DATE:

			defFormat = defFormat.replaceAll("D", "d");
			strFormat = defFormat.replaceAll("Y", "y");

			break;
		case DECIMAL:

			int lengthToFill = BASE_DECIMAL_FORMAT.length()
					+ countMatches(defFormat, "D");

			strFormat = EnumCommonFiller.ZERO_RIGHT.getOne().fill(
					BASE_DECIMAL_FORMAT, lengthToFill);

			break;
		}

		return strFormat;
	}
}
