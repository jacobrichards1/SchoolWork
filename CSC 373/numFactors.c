#include	<stdlib.h>
#include	<stdio.h>

const int BUFFER_LEN = 256;

void obtainNumber (const char* questionPtr, int* numberPtr)
{
  char	buffer[BUFFER_LEN];
  do 
  {
    printf("Please enter a %s (greater than 1): ", questionPtr);
     fgets(buffer,BUFFER_LEN,stdin);
  }
        while ((sscanf(buffer, "%d", numberPtr) < 0) || (*numberPtr < 2));
}

int countFactors (int dividend, int divisor)
{
  int count = 0;

  while(dividend % divisor == 0)
  {
  	dividend = dividend/divisor;
	count++;
  }
  return(count);
}

int main ()
{
  int	dividend;
  int	divisor;

  while  (1)
  {
    obtainNumber("dividend",&dividend);
    obtainNumber("divisor", &divisor);

    printf("%d goes into %d %d times.\n", divisor,dividend,countFactors(dividend,divisor));

  }

  return(EXIT_SUCCESS);
}
