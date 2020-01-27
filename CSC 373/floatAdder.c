#include <stdlib.h>
#include <stdio.h>

//  PURPOSE:  To tell how many bits to shift the sign bit from the least
//	signficant position to where the sign bit belongs.
  #define	 SIGN_SHIFT				31
//  PURPOSE:  To be the mask to only keep the sign bit.
  #define	 SIGN_MASK				(0x1 << SIGN_SHIFT)
//  PURPOSE:  To be the mask to keep everything but the sign bit.
 #define	 EVERYTHING_BUT_SIGN_MASK		(~SIGN_MASK)

//--			Exponent related constants			--//

//  PURPOSE:  To tell how many bits to shift the exponent bit field from the
//	least signficant position to where the exponent bit field belongs.
#define	 EXPONENT_SHIFT				23

//  PURPOSE:  To be the mask to only keep the exponent bit field.
#define	 EXPONENT_MASK			((unsigned)0xFF << EXPONENT_SHIFT)

//  PURPOSE:  To tell the exponent bit pattern for 'infinity' and
//	'not-a-number'.
#define	 EXPONENT_INFINITE_BIT_PATTERN		0xFF

//  PURPOSE:  To tell the exponent bit pattern for denormalized numbers
//	(including 0.0).
#define	 EXPONENT_DENORMALIZED_BIT_PATTERN	0x00

//  PURPOSE:  To tell the 'bias' of the exponent bit field:
//	(powerOf2) = (exponentBitPattern) - EXPONENT_BIAS
#define	 EXPONENT_BIAS				0x7F
//  PURPOSE:  To tell the power of 2 for 'infinity' and 'not-a-number'.
#define	 INFINITE_POWER_OF_2			+128

//  PURPOSE:  To tell the power of 2 for denormalized numbers (including 0.0):
#define	 DENORMALIZED_POWER_OF_2		-127

#define	 INDISTINGUISHABLE_FROM_0_POWER_OF_2	(DENORMALIZED_POWER_OF_2-23)

//--			Mantissa related constants			--//

//  PURPOSE:  To tell the mask to only keep the mantissa bit field.
#define	 MANTISSA_MASK				0x007FFFFF

//  PURPOSE:  To tell give the hidden bit in its proper position.
#define	 MANTISSA_HIDDEN_BIT			0x00800000

//  PURPOSE:  To tell how many bits to shift the mantissa bit field from the
//	least signficant position to where the mantissa bit field belongs.
#define	 MANTISSA_SHIFT				0

//  PURPOSE:  To tell how many mantissa bits there are (including hidden bit)
#define	 NUM_MANTISSA_BITS			24

//--			Miscellaneous related constants			--//

//  PURPOSE:  To give the maximum length of C-strings.
#define	 TEXT_LEN				64

//  PURPOSE:  To return 1 if 'f' is 0.0 or -0.0.  Returns 0 otherwise.

int 	      	isZero 	(float f)
{
unsigned int u = *(unsigned int*)&f;

	if((int)(u & EVERYTHING_BUT_SIGN_MASK) == 0)
	{
	return 1;
	}
	else{ 
	return 0;
	}
}
 

  
//  PURPOSE:  To return the +1 if the sign of 'f' is positive, or -1 otherwise.
int getSign (float f)
{
        unsigned int	u = *(unsigned int*)&f;
  
  	if((u & SIGN_MASK) == 0){
		return(+1);
	}
	else{
		return(-1);
	}
  
}


  //  PURPOSE:  To return the exponent (the X of 2^X) of the floating point
  //	'f' from 'DENORMALIZED_POWER_OF_2' to 'INFINITE_POWER_OF_2'.
  //	(Does _not_ return the bit pattern.)
  int		getPowerOf2	(float f)
  {
    unsigned int	u	= *(unsigned int*)&f;
    unsigned int 	i	=  u & EXPONENT_MASK;

	i = i >> EXPONENT_SHIFT;
	int exp = i - INFINITE_POWER_OF_2 + 1;
	if (exp < DENORMALIZED_POWER_OF_2) {
	return DENORMALIZED_POWER_OF_2;
	}
	else{
	return exp;
	}

          }

//  PURPOSE:  To return the mantissa of 'f', with the HIDDEN_BIT or-ed in if
//	 'f' is not denormalized.
  unsigned int	getMantissa	(float f)
  {
	if (f < 0) { f = -f;}
    unsigned int	mantissa	= *(unsigned int*)&f;
	mantissa = mantissa & MANTISSA_MASK;
	if (f > 1e-38){
	return(mantissa|MANTISSA_HIDDEN_BIT);
	}
	else{
   	return(mantissa);
	}


        }
//  PURPOSE:  To return the 0x0 when given +1, or 0x1 when given -1.
  unsigned char	signToSignBit	(int	sign)
  {
    if (sign < 0){
	return(0x1);
	}
    else{
	return(0x0);
	}

      }        
//  PURPOSE:  To return the exponent field's bit pattern for power of 2
//  //	'powerOf2'.  If 'powerOf2' is greater or equal to 'INFINITE_POWER_OF_2'
//  //	then it returns 'EXPONENT_INFINITE_BIT_PATTERN'.  If 'powerOf2' is
//  //	less than or equal to 'DENORMALIZED_POWER_OF_2' then it
//  //	returns 'EXPONENT_DENORMALIZED_BIT_PATTERN'.  Otherwise it returns the
//  //	corresponding bit pattern for 'powerOf2' given bias 'EXPONENT_BIAS'.

  unsigned char	pwrOf2ToExpBits	(int	powerOf2)
  
{
;
      if (powerOf2 >= INFINITE_POWER_OF_2){
	return (EXPONENT_INFINITE_BIT_PATTERN);
	}
      else if (powerOf2 <= DENORMALIZED_POWER_OF_2){
	return (EXPONENT_DENORMALIZED_BIT_PATTERN);
	}
      else{
	return (powerOf2 + EXPONENT_BIAS);
	}

      }


//  PURPOSE:  To return the mantissa _field_, 'mantissa' with its hidden
//  //	bit turned off.
  unsigned int  mantissaField	(unsigned int	mantissa )
 { 
  return(mantissa & MANTISSA_MASK);
 }


//  PURPOSE:  To return the floating point number constructed from sign bit
  //	'signBit'
  float	buildFloat	(int		sign,  	int	exp,	 unsigned int	mantissaBits)
{
//Leave this code alone
  unsigned int	u	= (signToSignBit(sign)		<< SIGN_SHIFT)	   |
			  (pwrOf2ToExpBits(exp)		<< EXPONENT_SHIFT) |
			  (mantissaField(mantissaBits)	<< MANTISSA_SHIFT);
  float 	f	= *(float*)&u;
  return(f);
}

//  PURPOSE:  To return 'f' added with 'g'.
float	add	(float	f, float g)
{
  //  I.  Handle when either 'f' or 'g' is 0.0:
      if  ( isZero(f) )
          return(g);
  
            if  ( isZero(g) )
                return(f);
  
  //  II.  Do operation:
	      
int		signF		= getSign(f);
  int		signG		= getSign(g);
  int		powerOf2F	= getPowerOf2(f);
  int		powerOf2G	= getPowerOf2(g);
  unsigned int	mantissaF	= getMantissa(f);
  unsigned int	mantissaG	= getMantissa(g);
  unsigned int	mantissa;
  int	   	powerOf2;
  int		sign;

  if  (signF == signG)
  {

//  II.A.  Do addition:
//  (This is required.)
//  See which has the bigger power-of-2: 'f' or 'g'
//  Set 'diff' equal to the difference between the powers
     unsigned int         diff;
	
	if(powerOf2F > powerOf2G){
	diff = powerOf2F - powerOf2G;
	}
	else
	{
	diff = powerOf2G - powerOf2F;
	}
	       

//  Keep this if-statement.
//  Unfortunately, (0x1 << 32) is 0x1, not 0x0.
//  This is because the CPU does a bitwise-and of the shift amount with 31
     if  (diff > NUM_MANTISSA_BITS)
   diff	= NUM_MANTISSA_BITS;
// Shift the mantissa of the smaller of the two numbers by 'diff'.
//  Then add the mantissas.
//  What is the value of 'powerOf2'?  What is the value of 'sign'?
//  How do you detect when the mantissa overflows?
//What do you do when the mantissa does overflow?

	sign = signF;

 if(sign == 1) {
	if (f > g)
	{
	mantissaG = (mantissaG)  >> diff;
	powerOf2 = powerOf2F;
	}
	else
	{
	mantissaF = (mantissaF) >> diff;
	powerOf2 = powerOf2G;
	}

	if((mantissaF & 0xF00000) + (mantissaG & 0xF00000) > 0xF00000)
	{
	mantissa = (mantissaF >> 1) + (mantissaG >> 1);
	powerOf2 = powerOf2 + 1;
	}
	else {
	mantissa = mantissaF + mantissaG;
	}
	printf("mantissa is = %0X6\n", mantissa);
}
 else{
	if (f < g)
        {
        mantissaG = (mantissaG) >> diff;
        powerOf2 = powerOf2F;
        }
        else
        {
        mantissaF = (mantissaF) >> diff;
        powerOf2 = powerOf2G;
        }

        if((mantissaF & 0xF00000) + (mantissaG & 0xF00000) > 0xF00000)
        {
        mantissa = (mantissaF >> 1) + (mantissaG >> 1);
        powerOf2 = powerOf2 + 1;
        }
        else {
        mantissa = mantissaF + mantissaG;
        }
        printf("mantissa is = %0X6\n", mantissa);

}


}
  else
  {
// II.B.  Do subtraction:
//  (This is +5 extra credit.)
//
//  Subtract the smaller from the bigger.
//  How do you tell which is bigger from 'powerOf2F', 'powerOf2G', 'mantissaF' and 'mantissaG'?
//                  //  Again, keep this if-statement.

unsigned int diff = 0;

	if(powerOf2F > powerOf2G){
        diff = powerOf2F - powerOf2G;
        }
        else if(powerOf2F < powerOf2G)
        {
        diff = powerOf2G - powerOf2F;
        }
	else
	{
	  diff = 0;
	}


if  (diff > NUM_MANTISSA_BITS)
      diff	= NUM_MANTISSA_BITS;

//  Do the same mantissa shifting as with addition.
//      //
  if (diff >= 0){
	if (f > g){
        mantissaG = (mantissaG)  >> diff;
        powerOf2 = powerOf2F;
        }
        else
        {
        mantissaF = (mantissaF) >> diff;
        powerOf2 = powerOf2G;
        }
	mantissa = mantissaF - mantissaF;
  }


	
 //  What is the value of 'powerOf2'?  What is the value of 'sign'?

 //  With addition you may be left with too many bits in the mantissa,
 //  with subtraction you may be left with too few.
 //  If that's the case, then keeping shifting the most significant bit
 //  in the mantissa until either it gets to the mantissa's most
 //  significant bit position (the hidden bit's position) or until
 //  'powerOf2' gets down to 'DENORMALIZED_POWER_OF_2'.
 //  Each time you shift 'mantissa' what should you do to 'powerOf2'?


  }

	

  //  III.  Return built float:
  //    //  Leave this code alone!
       return(buildFloat(sign,powerOf2,mantissa));
 }

//  PURPOSE:  To first test your 'getSign()', 'getPowerOf2()' and
//  //	'getMantissa()' functions, and then your 'add()' function.  Ignores
//  //	arguments from OS.  Returns 'EXIT_SUCCESS' to OS.

int	main	()
{
//leave this code
float	f;
  float	g;
  char	text[TEXT_LEN];

  do
  {
    printf("Please enter a floating point number or 0 to quit testing: ");
    fgets(text,TEXT_LEN,stdin);
    f = atof(text);

    printf("The sign     of %g is %+d\n",f,getSign(f));
    printf("The exponent of %g is 2^%d\n",f,getPowerOf2(f));
    printf("The mantissa of %g is 0x%06X\n",f,getMantissa(f));
    printf("The sign, exponent and mantissa reconstitute to form float %g\n",
	   buildFloat(getSign(f),getPowerOf2(f),getMantissa(f))
	  );
  }
  while  ( !isZero(f) );

  printf("\n\n");

  do
  {
    printf("Please enter the 1st floating point number to add: ");
    fgets(text,TEXT_LEN,stdin);
    f = atof(text);

    printf("Please enter the 2nd floating point number to add: ");
    fgets(text,TEXT_LEN,stdin);
    g = atof(text);

    printf("         You say  %g + %g == %g\n",f,g,add(f,g));
    printf("The hardware says %g + %g == %g\n",f,g,f+g);
  }
  while  ( !isZero(f) && !isZero(g) );

  return(EXIT_SUCCESS);
}                                                                         
