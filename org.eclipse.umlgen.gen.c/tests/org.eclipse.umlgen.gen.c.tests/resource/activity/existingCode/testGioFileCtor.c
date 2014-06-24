self->base.vtbl = (struct GioVtbl *) NULL;
self->iop = fopen(fname, mode);
if (NULL == self->iop) { 
	return 0;
	
}
self->base.vtbl = &kGioFileVtbl;
return 1;
