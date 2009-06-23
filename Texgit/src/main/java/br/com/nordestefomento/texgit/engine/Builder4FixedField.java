package br.com.nordestefomento.texgit.engine;

import static br.com.nordestefomento.jrimum.ACurbitaObject.isNotNull;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.apache.commons.lang.StringUtils.countMatches;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import br.com.nordestefomento.jrimum.utilix.Util4Date;
import br.com.nordestefomento.texgit.TexgitException;
import br.com.nordestefomento.texgit.language.EnumFormats;
import br.com.nordestefomento.texgit.language.EnumFormatsTypes;
import br.com.nordestefomento.texgit.language.MetaField;
import br.com.nordestefomento.texgit.type.component.EnumCommonFiller;
import br.com.nordestefomento.texgit.type.component.EnumSide;
import br.com.nordestefomento.texgit.type.component.Filler;
import br.com.nordestefomento.texgit.type.component.FixedField;

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
		
		case CHARACTER:
			FixedField<Character> fCHR = new FixedField<Character>();
			if (isNotBlank(metaField.getValue()))
				if(metaField.getValue().length() == 1)
					fCHR.setValue(metaField.getValue().charAt(0));
				else
					throw new IllegalArgumentException("Tipo character deve ter apenas 1!");
			else
				fCHR.setValue(' ');
			fField = fCHR;
			break;
		case STRING:
			FixedField<String> fSTR = new FixedField<String>();
			if (isNotBlank(metaField.getValue()))
				fSTR.setValue(metaField.getValue());
			else
				fSTR.setValue(StringUtils.EMPTY);
			fField = fSTR;
			break;
		case INTEGER:
			FixedField<Integer> fINT = new FixedField<Integer>();
			if (isNotBlank(metaField.getValue()))
				fINT.setValue(Integer.parseInt(metaField.getValue()));
			else
				fINT.setValue(new Integer(0));
			fField = fINT;
			break;
		case LONG:
			FixedField<Long> fLNG = new FixedField<Long>();
			if (isNotBlank(metaField.getValue()))
				fLNG.setValue(Long.parseLong(metaField.getValue()));
			else
				fLNG.setValue(new Long(0));
			fField = fLNG;
			break;
		case FLOAT:
			FixedField<Float> fFLT = new FixedField<Float>();
			if (isNotBlank(metaField.getValue()))
				fFLT.setValue(Float.parseFloat(metaField.getValue()));
			else
				fFLT.setValue(new Float(0));
			fField = fFLT;
			break;
		case DOUBLE:
			FixedField<Double> fDBE = new FixedField<Double>();
			if (isNotBlank(metaField.getValue()))
				fDBE.setValue(Double.parseDouble(metaField.getValue()));
			else
				fDBE.setValue(new Double(0));
			fField = fDBE;
			break;
		case BIGDECIMAL:
			FixedField<BigDecimal> fBDL = new FixedField<BigDecimal>();
			if (isNotBlank(metaField.getValue()))
				fBDL.setValue(new BigDecimal(DecimalFormat.class
						.cast(formatter).parse(metaField.getValue())
						.doubleValue()));
			else
				fBDL.setValue(new BigDecimal(0));
			fField = fBDL;
			break;
		case DATE:
			FixedField<Date> fDTE = new FixedField<Date>();
			if (isNotBlank(metaField.getValue()))
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
		fField.setBlankAccepted(metaField.isBlankAccepted());
		
		if(isNotNull(formatter))
			fField.setFormatter(formatter);

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
			if(isNotNull(metaField.getFormat())){
			
				EnumFormats format = metaField.getFormat(); 
				
				EnumFormatsTypes type = EnumFormatsTypes.valueOf(format.name()
						.split("_")[0]);

				formatter = buildFormat(buildFormat(format, type), type);
			}
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

		String strFormat = StringUtils.EMPTY;
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
