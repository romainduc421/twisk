#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>
#include <fcntl.h>
#include <math.h>


/**
*
*
*/
void delaiPoisson(double lambda)
{
  // Determine U
  double u = ((double) rand() / (double)RAND_MAX) ;

  int c = -log(u)/(1/lambda);

  sleep(c);
}

/**
*
*
*/
void delaiGauss(double moyenne, double ecartype)
{
  // Determine U1 et U2
  double u = ((double) rand() / (double)RAND_MAX) ;
  double v = ((double) rand() / (double)RAND_MAX);

  int c=-1;
  while(c<1)
  {
    u = ((double) rand() / (double)RAND_MAX) ;
    v = ((double) rand() / (double)RAND_MAX) ;
    c = (sqrt(-2*log(u))) * (cos(2*M_PI*v)*ecartype) + moyenne;
  }

  usleep(c);
}

/**
*
*
*/
void delaiUniforme(int temps, int delta)
{

  int bi, bs ;
  int n, nbSec ;
  bi = temps - delta;
  if (bi < 0)
    bi = 0;
  bs = temps + delta;
  n = bs - bi;
  nbSec = (rand()/ (float)RAND_MAX) * n;
  nbSec += bi;

  sleep(nbSec);
}

