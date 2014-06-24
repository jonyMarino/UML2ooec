struct GioFile  *self = (struct GioFile *) vself;
int             ret;
ret = fseek(self->iop, (long) offset, whence);
return (off_t) ftell(self->iop);