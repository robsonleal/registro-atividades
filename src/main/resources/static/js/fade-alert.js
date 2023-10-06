document.addEventListener('DOMContentLoaded', function() {
  setTimeout(function() {
    var alertElement = document.querySelector('.alert');
    var bsAlert = new bootstrap.Alert(alertElement);
    bsAlert.close();
  }, 5000);
});
